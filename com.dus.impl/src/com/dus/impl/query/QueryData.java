package com.dus.impl.query;

import java.util.UUID;

import com.dus.base.finder.FetchOptions;
import com.dus.spi.query.IQueryRequest;

public class QueryData implements IQueryRequest {
	private final String finderId;
	private final String finderMethod;
	private final FetchOptions fOptions;
	
	private final String id = UUID.randomUUID().toString();
	private final Object[] parameters;
	
	public QueryData(String finderId, String finderMethod, FetchOptions fOptions, Object[] parameters) {
		this.finderId = finderId;
		this.finderMethod = finderMethod;
		this.fOptions = fOptions;
		this.parameters = parameters;
	}
	
	@Override
	public String getFinderId() {return finderId;}

	@Override
	public String getFinderMethod() {return finderMethod;}

	@Override
	public FetchOptions getFetchOptions() {return fOptions;}

	@Override
	public String getId() {return id;}

	@Override
	public Object[] getParameters() {return parameters;}

}
