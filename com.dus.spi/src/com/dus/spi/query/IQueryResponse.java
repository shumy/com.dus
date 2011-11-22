package com.dus.spi.query;

import java.util.Set;

import com.dus.base.EntityID;
import com.dus.base.schema.SProperty;
import com.dus.spi.container.ITree;

public interface IQueryResponse {
	String getId();
	boolean isSingleResult();
	
	EntityID getSingleResult();
	Set<EntityID> getResults();
	
	ITree<EntityID, SProperty, Object> getValues();
}
