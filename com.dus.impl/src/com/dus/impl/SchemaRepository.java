package com.dus.impl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import com.dus.ISchemaRepository;
import com.dus.base.ClientActions;
import com.dus.base.IEntity;
import com.dus.base.schema.SEntity;
import com.dus.container.IRList;
import com.dus.impl.ReflectionHelper.MethodType;
import com.dus.impl.entity.IMethodExecutor;
import com.dus.impl.entity.schema.Executor_action;
import com.dus.impl.entity.schema.SActionImpl;
import com.dus.impl.entity.schema.SEntityImpl;
import com.dus.impl.entity.schema.SPropertyImpl;

public class SchemaRepository implements ISchemaRepository {
		
	//entity schemas
	private final Map<Class<?>, SEntity> schemas = new HashMap<Class<?>, SEntity>();
	
	@Override
	@SuppressWarnings("unchecked")
	public void register(Class<? extends IEntity> type) {
		SEntityImpl entity = getOrRegister(type);
		
		for(Class<?> i_nterface: type.getInterfaces())
			if(i_nterface != IEntity.class && IEntity.class.isAssignableFrom(i_nterface)) {
				SEntity iEntity = getOrRegister((Class<? extends IEntity>)i_nterface);
				entity.addComponent(iEntity);
			}
		
		schemas.put(type, entity);
		System.out.println(entity);
	}
	
	@Override
	public SEntity getSchemaFor(Class<? extends IEntity> type) {
		SEntity schema = schemas.get(type);
		if(schema == null) throw new RuntimeException("No schema found for: " + type.getName());
		
		return schemas.get(type);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public SEntity getSchemaFor(String typeName) {
		try {
			Class<? extends IEntity> type = (Class<? extends IEntity>) this.getClass().getClassLoader().loadClass(typeName);
			return getSchemaFor(type);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	private SEntityImpl getOrRegister(Class<? extends IEntity> i_nterface) {
		SEntityImpl rSchema = (SEntityImpl) schemas.get(i_nterface);
		if(rSchema == null) {
			rSchema = new SEntityImpl(i_nterface);
			for(Method method: i_nterface.getDeclaredMethods()) {
				MethodType mType = ReflectionHelper.getPropertyMethodType(method.getName());
				if(mType == MethodType.GET || mType == MethodType.IS) {
					String propName = ReflectionHelper.getPropertyName(mType, method.getName());
				
					boolean isMany = IRList.class.isAssignableFrom(method.getReturnType());
					Class<?> type = null;
					if(isMany) {
						ParameterizedType rType = (ParameterizedType) method.getGenericReturnType();
						type = (Class<?>) rType.getActualTypeArguments()[0];
					} else
						type = method.getReturnType();
					
					SPropertyImpl property = new SPropertyImpl(propName, type);
					
					property.setMany(isMany);
					property.setRead(true);
					
					rSchema.addProperty(property);
				} else if(mType == MethodType.SET) {
					String propName = ReflectionHelper.getPropertyName(mType, method.getName());
					SPropertyImpl property = (SPropertyImpl) rSchema.getPropertyByName(propName);
					if(property == null) {
						property = new SPropertyImpl(propName, method.getParameterTypes()[0]);
						rSchema.addProperty(property);
					}
					
					property.setWrite(true);
				} else if(mType == MethodType.ACTION) {
					SActionImpl action = new SActionImpl(method.getName());
					rSchema.addAction(action);
				}
			}
			
			for(Class<?> innerClass: i_nterface.getDeclaredClasses()) {
				if(innerClass.getAnnotation(ClientActions.class) != null) {	//class of actions...
					for(Method method: innerClass.getDeclaredMethods()) {
						SActionImpl action = (SActionImpl) rSchema.getActionByName(method.getName());
						
						if(action != null) {
							IMethodExecutor executor = new Executor_action(innerClass, method);
							action.setExecutor(executor);
						} else {
							throw new RuntimeException("No action (" + method.getName() + ") found on enity: " + rSchema.getType());
						}
					}
				}
			}
			
			schemas.put(i_nterface, rSchema);
		}
		
		return rSchema;
	}
	
}
