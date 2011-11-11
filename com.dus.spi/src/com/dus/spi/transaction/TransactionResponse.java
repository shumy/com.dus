package com.dus.spi.transaction;

import java.util.Collections;
import java.util.Map;

import com.dus.base.EntityID;

public class TransactionResponse implements ITransactionResponse {
	private final String id;
	
	//<tmp-id> <server-id>
	private final Map<EntityID, EntityID> idMap;

	public TransactionResponse(String id, Map<EntityID, EntityID> idMap) {
		this.id = id;
		this.idMap = Collections.unmodifiableMap(idMap);
	}
	
	@Override
	public String getId() {return id;}
	
	@Override
	public Map<EntityID, EntityID> getIdMap() {return idMap;}

}
