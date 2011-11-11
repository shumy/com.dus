package com.dus.impl.entity;

import com.dus.base.schema.SProperty;

public class Executor_rUnset implements IMethodExecutor {

	@Override
	public Object execute(EntityProxyHandler handler, Object... parameters) {
		SProperty property = (SProperty) parameters[0];
		if(handler.isCreated() && !property.isWrite()) throw new RuntimeException("Property ("+ property.getName() +") from ("+ handler.getId().schema.getType().getName() +") is not writable!");
		
		// TODO Auto-generated method stub
		
		return null;
	}

}
