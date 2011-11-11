package com.dus.impl.entity.schema;

import com.dus.base.schema.SProperty;

public class SPropertyImpl implements SProperty {
	private final String name;
	private final Class<?> type;
	
	private boolean isMany = false;
	private boolean isWrite = false;
	private boolean isRead = false;
	
	public SPropertyImpl(String name, Class<?> type) {
		this.name = name;
		this.type = type;
	}

	public void setMany(boolean isMany) {this.isMany = isMany;}
	
	public void setWrite(boolean isWrite) {this.isWrite = isWrite;}
	
	public void setRead(boolean isRead) {this.isRead = isRead;}
	
	@Override
	public String getName() {return name;}

	@Override
	public Class<?> getType() {return type;}
	
	@Override
	public boolean isMany() {return isMany;}

	@Override
	public boolean isWrite() {return isWrite;}
	
	@Override
	public boolean isRead() {return isRead;}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("name=");
		sb.append(name);
		sb.append(", type=");
		sb.append(type.getName());
		sb.append(", isMany=");
		sb.append(isMany);
		sb.append(", isRead=");
		sb.append(isRead);
		sb.append(", isWrite=");
		sb.append(isWrite);
		sb.append("}");
		return sb.toString();
	}
}
