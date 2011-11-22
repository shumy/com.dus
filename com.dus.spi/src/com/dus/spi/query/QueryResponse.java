package com.dus.spi.query;

import java.util.Collections;
import java.util.Set;

import com.dus.base.EntityID;
import com.dus.base.schema.SProperty;
import com.dus.spi.container.ITree;
import com.dus.spi.container.Tree;

public class QueryResponse implements IQueryResponse {

	private final String id;

	private boolean isSingleResult;
	
	private final EntityID singleResult;
	private final Set<EntityID> results;
	private final ITree<EntityID, SProperty, Object> values;
	
	public QueryResponse(String id, Set<EntityID> results, Tree<EntityID, SProperty, Object> values) {
		this.id = id;
		this.isSingleResult = false;
		this.singleResult = null;
		this.results = Collections.unmodifiableSet(results);
		this.values = values;
	}
	
	public QueryResponse(String id, EntityID singleResult, Tree<EntityID, SProperty, Object> values) {
		this.id = id;
		this.isSingleResult = true;
		this.singleResult = singleResult;
		this.results = null;
		this.values = values;
	}
	
	@Override
	public String getId() {return id;}
	
	@Override
	public boolean isSingleResult() {return isSingleResult;}
	
	@Override
	public EntityID getSingleResult() {return singleResult;}
	
	@Override
	public Set<EntityID> getResults() {return results;}
	
	@Override
	public ITree<EntityID, SProperty, Object> getValues() {return values;}
}
