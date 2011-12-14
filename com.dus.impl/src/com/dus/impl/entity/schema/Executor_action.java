package com.dus.impl.entity.schema;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.dus.base.IProperty;
import com.dus.base.schema.SProperty;
import com.dus.impl.BaseProperty;
import com.dus.impl.entity.EntityProxyHandler;
import com.dus.impl.entity.IMethodExecutor;

public class Executor_action implements IMethodExecutor {
	private Class<?> actionClass; 
	private Method method;
	
	public Executor_action(Class<?> actionClass, Method method) {
		this.actionClass = actionClass;
		this.method = method;
		method.setAccessible(true);
	}
	
	@Override
	public Object execute(EntityProxyHandler handler, Object... parameters) {
		System.out.println("Action executed: " + method.getName());
		
		try {
			Object actionObj = actionClass.newInstance();
			
			//TODO: optimize this...
			for(Field field: actionClass.getDeclaredFields()) {
				if(field.getType() == IProperty.class) {
					field.setAccessible(true);
					
					SProperty property = handler.getId().schema.getPropertyByName(field.getName());
					field.set(actionObj, new BaseProperty(handler, property));				
				}
			}
			
			return method.invoke(actionObj, parameters);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
