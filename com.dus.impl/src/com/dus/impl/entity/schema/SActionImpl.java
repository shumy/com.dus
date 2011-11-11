package com.dus.impl.entity.schema;

import com.dus.base.schema.action.IActionExecutor;
import com.dus.base.schema.action.SAction;

public class SActionImpl implements SAction {
	private final String name;
	private boolean isEnabled;
	private IActionExecutor executor;

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

	@Override
	public IActionExecutor getExecutor() {return executor;}

	@Override
	public void setExecutor(IActionExecutor executor) {this.executor = executor;}

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
