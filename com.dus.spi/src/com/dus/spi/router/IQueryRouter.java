package com.dus.spi.router;

import com.dus.spi.query.IQueryRequest;
import com.dus.spi.query.IQueryResponse;


public interface IQueryRouter {
	
	IQueryResponse execute(IQueryRequest qRequest);
	
	//TODO: support for lazy loading
}
