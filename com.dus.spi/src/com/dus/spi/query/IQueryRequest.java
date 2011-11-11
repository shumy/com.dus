package com.dus.spi.query;

import com.dus.base.EntityID;
import com.dus.base.finder.FetchConfig;


public interface IQueryRequest {
	public enum QProtocol {
		Q_ID,
		Q_FILTER,
		Q_DSQL,
		Q_SEARCH,
	}
	
	String getId();
	
	QProtocol getProtocol();
	
	EntityID getResultType();
	
	String getQueryText();
	
	Object[] getParameters();
	
	FetchConfig getLoadConfigs();
}
