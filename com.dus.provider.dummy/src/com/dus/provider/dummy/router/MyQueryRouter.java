package com.dus.provider.dummy.router;

import com.dus.Dus;
import com.dus.ISchemaRepository;
import com.dus.base.EntityID;
import com.dus.spi.query.IQueryRequest;
import com.dus.spi.query.IQueryResponse;
import com.dus.spi.query.QueryResponse;
import com.dus.spi.router.IQueryRouter;

public class MyQueryRouter implements IQueryRouter {

	@Override
	public IQueryResponse execute(IQueryRequest qRequest) {
		ISchemaRepository repo = Dus.getSchemaRepository();
		
		String id = (String) qRequest.getParameters()[0];
		
		EntityID result = new EntityID(repo.getSchemaFor("test.domain.User"), id);
		
		QueryResponse qResponse = new QueryResponse(qRequest.getId(), result , null);
		return qResponse;
	}

}
