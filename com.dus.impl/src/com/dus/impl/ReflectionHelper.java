package com.dus.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.dus.base.IEntity;

public class ReflectionHelper {
	public static enum MethodType {IS, GET, SET, ACTION}
	
	public static Object getValue(Field field, Object object) {
		try {
			return field.get(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getPropertyName(MethodType type, String methodName) {
		String name = null;
		switch (type) {
			case IS: name = methodName.substring(2);
			
			case SET:
			case GET: name = methodName.substring(3);
		}
	
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}
	
	public static MethodType getPropertyMethodType(String methodName) {
		if(methodName.startsWith("get")) {
			return MethodType.GET;
		} else if(methodName.startsWith("is")) {
			return MethodType.IS;
		} else if(methodName.startsWith("set")) {
			return MethodType.SET;
		}
		
		return MethodType.ACTION;
	}
	
	public static IEntity createProxy(Class<?> i_nterface, InvocationHandler handler){
		return (IEntity) Proxy.newProxyInstance(i_nterface.getClassLoader(), new Class<?>[] {i_nterface}, handler);
	}
}
