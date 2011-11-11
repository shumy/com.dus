package com.dus.base.notification;

import com.dus.base.EntityID;
import com.dus.base.schema.SProperty;

public final class Notification {
	public enum Type {
		SET, UNSET,
		ADD, REMOVE
	}
	
	public final EntityID entityId;
	public final SProperty property;
	public final Type type;
	
	public final Object oldValue;
	public final Object newValue;
	public final int listIndex;
	
	public Notification(EntityID entityId, SProperty property, Type type,
			Object oldValue, Object newValue, int listIndex) {
		
		this.entityId = entityId;
		this.property = property;
		this.type = type;
		
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.listIndex = listIndex;
	}
}
