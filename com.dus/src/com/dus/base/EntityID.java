package com.dus.base;

import com.dus.base.schema.SEntity;

public final class EntityID {
	public final SEntity schema;
	public final String clientId;
	
	public EntityID(SEntity schema, String clientId) {
		this.schema = schema;
		this.clientId = clientId;
	}
	
	@Override
	public int hashCode() {
		return schema.hashCode() ^ clientId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj == this) return true;
		
		if(obj instanceof EntityID) {
			EntityID other = (EntityID) obj;
			if(schema.getType().equals(other.schema.getType()) && clientId.equals(other.clientId))
				return true;
			else 
				return false;
		} else 
			return false;
		
	}
	
	@Override
	public String toString() {
		return "("+schema.getType().getName() + ":" + clientId+")";
	}
}
