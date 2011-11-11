package com.dus.spi.query;

import java.util.Set;

import com.dus.base.EntityID;
import com.dus.spi.ITree;

public interface IQueryResponse {
	String getId();
	
	Set<EntityID> getResults();
	
	ITree<EntityID, String, Object> getValues();
}
