package com.dus.spi.store;

import java.util.List;

import com.dus.base.EntityID;
import com.dus.base.IEntity;
import com.dus.base.schema.SProperty;

public interface IStore {
	IEntity getEntity(EntityID id);
	void mapEntity(EntityID id, IEntity entity);
	
	Object getProperty(EntityID id, SProperty property);
	void setProperty(EntityID id, SProperty property, Object newValue);
	
	
	boolean containsReference(EntityID id, SProperty property, EntityID ref);
	List<EntityID> getReferenceList(EntityID id, SProperty property);
	int getSizeOfRefList(EntityID id, SProperty property);
	
	void removeReference(EntityID id, SProperty property, int index);
	int removeReference(EntityID id, SProperty property, EntityID ref);
	
	void addReference(EntityID id, SProperty property, EntityID ref, int index);
	int addReference(EntityID id, SProperty property, EntityID ref);

}
