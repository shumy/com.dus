package com.dus.spi.transaction;

import java.util.Set;

import com.dus.base.EntityID;
import com.dus.base.schema.SProperty;
import com.dus.spi.ITree;

public interface ITransactionRequest {
	interface IAddRemoveReport {
		Set<EntityID> getAddEntities();
		Set<EntityID> getRemoveEntities();
	}
	
	String getId();
	
	Set<EntityID> getNewEntities();
	Set<EntityID> getDeleteEntities();
	
	ITree<EntityID, SProperty, Object> getProperties();
	ITree<EntityID, SProperty, IAddRemoveReport> getReferences();
}
