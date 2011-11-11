package com.dus.spi.query;

import java.util.Collections;
import java.util.Set;

import com.dus.base.EntityID;
import com.dus.spi.ITree;
import com.dus.spi.Tree;

public class QueryResponse implements IQueryResponse {

	private final String id;
	
	private final Set<EntityID> results;
	private final ITree<EntityID, String, Object> values;
	
	public QueryResponse(String id, Set<EntityID> results, Tree<EntityID, String, Object> values) {
		this.id = id;
		this.results = Collections.unmodifiableSet(results);
		this.values = values;
	}
	
	@Override
	public String getId() {return id;}
	
	@Override
	public Set<EntityID> getResults() {return results;}
	
	@Override
	public ITree<EntityID, String, Object> getValues() {return values;}
}
