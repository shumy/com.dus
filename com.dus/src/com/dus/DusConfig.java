package com.dus;

import java.util.HashMap;

public class DusConfig {
	private HashMap<Class<?>, Object> instances = new HashMap<Class<?>, Object>();
	
	public void setInstanceOf(Class<?> type, Object object) {
		instances.put(type, object);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getInstanceOf(Class<?> type) {
		return (T) instances.get(type);
	}
}
