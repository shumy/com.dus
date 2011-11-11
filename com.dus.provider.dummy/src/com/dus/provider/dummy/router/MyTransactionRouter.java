package com.dus.provider.dummy.router;

import java.util.HashMap;
import java.util.Map;

import com.dus.base.EntityID;
import com.dus.spi.router.ITransactionRouter;
import com.dus.spi.transaction.ITransactionRequest;
import com.dus.spi.transaction.ITransactionResponse;
import com.dus.spi.transaction.TransactionResponse;

public class MyTransactionRouter implements ITransactionRouter {

	@Override
	public ITransactionResponse commit(ITransactionRequest txRequest) {
		System.out.println(txRequest);
		
		Map<EntityID, EntityID> idMap = new HashMap<EntityID, EntityID>();
		for(EntityID id: txRequest.getNewEntities()) {
			idMap.put(id, new EntityID(id.schema, id.clientId.substring(3)));
		}

		return new TransactionResponse(txRequest.getId(), idMap);
	}

}
