package com.dus.base.schema;

public interface SProperty extends SFeature {
	Class<?> getType();
	
	boolean isMany();
	boolean isWrite();
	boolean isRead();
}
