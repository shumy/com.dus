package com.dus.spi.router;

import com.dus.spi.transaction.ITransactionRequest;
import com.dus.spi.transaction.ITransactionResponse;

public interface ITransactionRouter {
	ITransactionResponse commit(ITransactionRequest txRequest);
	
	//TODO: support for large data transfers
	
	//TODO: support for server data validation
}
