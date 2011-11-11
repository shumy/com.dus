package com.dus.impl;

import com.dus.Dus.IBridge;
import com.dus.spi.router.IQueryRouter;
import com.dus.spi.router.ITransactionRouter;
import com.dus.spi.store.Store;
import com.dus.DusConfig;
import com.dus.ISchemaRepository;
import com.dus.ISession;

public class Bridge implements IBridge {

	@Override
	public ISchemaRepository createRepository() {
		return new SchemaRepository();
	}

	@Override
	public ISession createSession(ISchemaRepository repo, DusConfig configs) {
		ITransactionRouter tRouter = configs.getInstanceOf(ITransactionRouter.class);
		IQueryRouter qRouter = configs.getInstanceOf(IQueryRouter.class);
		
		if(tRouter == null) throw new RuntimeException("No transaction router defined!");
		if(qRouter == null) throw new RuntimeException("No query router defined!");
		
		return new Session(repo, new Store(), tRouter, qRouter);
	}

}
