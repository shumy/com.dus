package com.dus.spi.query;

import com.dus.base.finder.FetchOptions;

public interface IQueryRequest {
	String getFinderId();
	String getFinderMethod();
	FetchOptions getFetchOptions();
	
	String getId();
	Object[] getParameters();
}
