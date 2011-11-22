package com.dus.spi.container;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dus.base.EntityID;
import com.dus.base.IEntity;
import com.dus.base.schema.SProperty;

public class Store implements IStore {
	
	//all data in the session
	private final Map<EntityID, IEntity> entities = new HashMap<EntityID, IEntity>();
	
	private final Tree<EntityID, SProperty, Object> properties = new Tree<EntityID, SProperty, Object>();
	
	@Override
	public IEntity getEntity(EntityID id) {
		return entities.get(id);
	}
	
	@Override
	public void mapEntity(EntityID id, IEntity entity) {
		entities.put(id, entity);
	}
	
	
	//-----------------------------------------------------------------------------------------------
	@Override
	public Object getProperty(EntityID id, SProperty property) {
		Object value = properties.get(id, property);
		
		//System.out.println("STORE GET: " + id + " P" + property + " V=" + value);
		return value;
	}
	
	@Override
	public void setProperty(EntityID id, SProperty property, Object newValue) {
		//System.out.println("STORE SET: " + id + " P" + property + " V=" + newValue);
		properties.set(id, property, newValue);
	}
	
	
	//----------------------------------------------------------------------------------------------------
	@Override
	public boolean containsReference(EntityID id, SProperty property, EntityID ref) {
		List<EntityID> refs = getOrCreateReferences(id, property);
		return refs.contains(ref);
	}
	
	@Override
	public int getSizeOfRefList(EntityID id, SProperty property) {
		List<EntityID> refs = getOrCreateReferences(id, property);
		return refs.size();
	}
	
	@Override
	public List<EntityID> getReferenceList(EntityID id, SProperty property) {
		return getOrCreateReferences(id, property);
	}
	
	@Override
	public void removeReference(EntityID id, SProperty property, int index) {
		List<EntityID> refs = getOrCreateReferences(id, property);
		refs.remove(index);
	}
	
	@Override
	public void addReference(EntityID id, SProperty property, EntityID ref, int index) {
		List<EntityID> refs = getOrCreateReferences(id, property);
		refs.add(index, ref);
	}
	
	@Override
	public int addReference(EntityID id, SProperty property, EntityID ref) {
		//System.out.println("STORE ADD: " + id + " P" + property + " V=" + ref);
		List<EntityID> refs = getOrCreateReferences(id, property);
		refs.add(ref);
		return refs.indexOf(ref);
	}
	
	@Override
	public int removeReference(EntityID id, SProperty property, EntityID ref) {
		List<EntityID> refs = getOrCreateReferences(id, property);
	
		int index = refs.indexOf(ref);
		refs.remove(ref);
		return index;
	}	
	
	//------------------------------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	private List<EntityID> getOrCreateReferences(EntityID id, SProperty property) {
		List<EntityID> refs = (List<EntityID>) properties.get(id, property);
		if(refs == null) {
			refs = new LinkedList<EntityID>();
			properties.set(id, property, refs);
		}
		return refs;
	}
}
