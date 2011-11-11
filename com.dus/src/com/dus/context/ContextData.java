package com.dus.context;

import java.util.HashMap;

import com.dus.ISession;

public class ContextData {
	private final HashMap<Class<?>, Object> contextObjects = new HashMap<Class<?>, Object>();
	private ISession session;
	
	@SuppressWarnings("unchecked")
	public <T> T getContextObject(Class<T> type) {
		return (T) contextObjects.get(type);
	}
	
	public void setContextObject(Class<?> type, Object object) {
		contextObjects.put(type, object);
	}
	
	public ISession getSession() {return session;}
	public void setSession(ISession session) {
		this.session = session;
	}
}
