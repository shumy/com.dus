package com.dus.spi.transaction;

import java.util.Map;

import com.dus.base.EntityID;

public interface ITransactionResponse {
	String getId();
	
	Map<EntityID, EntityID> getIdMap();
}
