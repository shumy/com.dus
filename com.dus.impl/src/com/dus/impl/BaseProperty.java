package com.dus.impl;

import com.dus.base.IProperty;
import com.dus.base.schema.SProperty;
import com.dus.impl.entity.EntityProxyHandler;

public class BaseProperty implements IProperty<Object> {
	private EntityProxyHandler handler;
	private SProperty property;

	public BaseProperty(EntityProxyHandler handler, SProperty property) {
		this.handler = handler;
		this.property = property;
	}
	
	
	@Override
	public Object get() {
		return handler.getPropertyValue(property);
	}

	@Override
	public void set(Object value) {
		handler.setPropertyValue(property, value);
	}

}
