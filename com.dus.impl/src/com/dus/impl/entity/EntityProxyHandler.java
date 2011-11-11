package com.dus.impl.entity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dus.base.EntityID;
import com.dus.base.IEntity;
import com.dus.base.notification.INotificationListener;
import com.dus.base.notification.Notification;
import com.dus.base.schema.SProperty;
import com.dus.base.schema.action.IActionExecutor;
import com.dus.base.schema.action.SAction;
import com.dus.impl.ReflectionHelper;
import com.dus.impl.ReflectionHelper.MethodType;
import com.dus.spi.store.IStore;

public class EntityProxyHandler implements InvocationHandler {
	private static final Map<String, IMethodExecutor> entityMethods = new HashMap<String, IMethodExecutor>();
	static {
		//IReflected
		entityMethods.put("rGet", new Executor_rGet());
		entityMethods.put("rSet", new Executor_rSet());
		entityMethods.put("rUnset", new Executor_rUnset());
		//rInvoke not used here...
		
		//INotifiable
		//entityMethods.put("getListeners", value);
		
		//IEntity
		entityMethods.put("validate", new Executor_validate());
		
	}
	
	private final EntityID id;
	private final IStore store;
	private final List<INotificationListener> listeners = new LinkedList<INotificationListener>();
	
	private boolean isCreated = false;
	private boolean isDeleted = false;
	private boolean isNotificationsEnabled = false;
	

	public EntityProxyHandler(EntityID id, IStore store) {
		this.id = id;
		this.store = store;
		store.mapEntity(id, ReflectionHelper.createProxy(id.schema.getType(), this));
	}
	
	public EntityID getId() {return id;}
	
	public IEntity getEntity() {return store.getEntity(id);}
	
	public IStore getStore() {return store;}
	
	public void setCreated() {isCreated = true;}
	public boolean isCreated() {return isCreated;}
	
	public void notify(Notification notification) {
		if(isNotificationsEnabled)
			for(INotificationListener listener: listeners)
				listener.notify(notification);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] parameters) throws Throwable {
		if(isDeleted) throw new RuntimeException("Can't work with deleted entity!");
		
		final String methodName = method.getName();
		
		if(methodName.equals("getId")) {
			return id;
		} else if(methodName.equals("isNotificationsEnabled")) {
			return isNotificationsEnabled;
		} else if(methodName.equals("setNofiticationsEnabled")) {
			isNotificationsEnabled = (Boolean) parameters[0];
			return null;
		} else if(methodName.equals("toString")) {
			return toString();
		} else if(methodName.equals("hashCode")) {
			return hashCode();
		} else if(methodName.equals("equals")) {
			return proxy.equals(parameters[0]);
		} else {
			MethodType mType = ReflectionHelper.getPropertyMethodType(methodName);
			switch (mType) {	
				case IS:
				case GET:
					return entityMethods.get("rGet").execute(this, getProperty(mType, methodName));
					
				case SET:
					entityMethods.get("rSet").execute(this, getProperty(mType, methodName), parameters[0]);
					return getEntity();
					
				case ACTION:
					if(isEntityMethod(methodName)) 
						return entityMethods.get(methodName).execute(this, parameters);
					else
						return executeUserMethod(methodName, parameters);	//execute user method
			}
			return null;
		}
	}
		
	//--------------------------------------------------------------------------------------------------------------------
	//Help methods--------------------------------------------------------------------------------------------------------
	private boolean isEntityMethod(String methodName) {
		return entityMethods.containsKey(methodName);
	}
	
	private Object executeUserMethod(String methodName, Object[] parameters) {
		SAction action = null;
		Object[] parametersForAction = null;
		if(methodName.equals("rInvoke")) {
			action = (SAction) parameters[0];
			parametersForAction = Arrays.copyOfRange(parameters, 1, parameters.length);
		} else {
			action = id.schema.getActionByName(methodName);
			parametersForAction = parameters;
		}

		if(action.isEnabled()) {
			IActionExecutor executor = action.getExecutor();
			return executor.execute(getEntity(), parametersForAction);
		}
		
		throw new RuntimeException("Executor not enabled/available for action: " + action.getName() + " in " + id.schema.getType().getName());
	}
		
	private SProperty getProperty(MethodType mType, String methodName) {
		String name = ReflectionHelper.getPropertyName(mType, methodName);
		SProperty property = id.schema.getPropertyByName(name);
		if(property == null) throw new RuntimeException("Property ("+ name +") not available in: "+ id.schema.getType().getName());
		
		return property;
	}
	
	@Override
	public String toString() {		
		StringBuilder sb = new StringBuilder("ENTITY: ");
		sb.append(id);
		
		int index = 0;
		sb.append("\n  PROPERTIES: {");
		for(SProperty property: id.schema.getProperties()) {
			sb.append(property.getName());
			sb.append("=");
			
			if(property.isMany()) {
				int size = store.getSizeOfRefList(id, property);
				
				sb.append("[list-of=");
				sb.append(property.getType().getName());
				sb.append(", size=");
				sb.append(size);
				sb.append("]");
			} else	
				sb.append(store.getProperty(id, property));
			
			index++;
			if(index == id.schema.getProperties().size()) break;
			sb.append(", ");
		}
				
		sb.append("}");
		return sb.toString();
	}
}