package com.dus.impl.entity;

public class Executor_validate implements IMethodExecutor {

	@Override
	public Object execute(EntityProxyHandler handler, Object... parameters) {
		System.out.println("VALIDATE: " + handler.getId());
		return true;
	}

}
