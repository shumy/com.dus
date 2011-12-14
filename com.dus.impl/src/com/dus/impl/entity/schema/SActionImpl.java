package com.dus.impl.entity.schema;

import com.dus.base.schema.SAction;
import com.dus.impl.entity.IMethodExecutor;

public class SActionImpl implements SAction {
	private final String name;
	private boolean isEnabled;
	private IMethodExecutor executor;

	public SActionImpl(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {return name;}

	@Override
	public boolean isEnabled() {
		return executor == null? false: isEnabled;
	}

	@Override
	public void setEnabled(boolean isEnabled) {this.isEnabled = isEnabled;}

	public IMethodExecutor getExecutor() {return executor;}
	
	public void setExecutor(IMethodExecutor executor) {
		this.executor = executor;
		isEnabled = true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("name=");
		sb.append(name);
		sb.append(", enabled=");
		sb.append(isEnabled);
		
		if(isEnabled) {
			sb.append(", executor=");
			sb.append(executor.getClass().getName());
		}
		
		sb.append("}");
		return sb.toString();
	}
}
