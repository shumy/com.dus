package com.dus.impl;

import java.lang.reflect.Field;
import java.util.UUID;

import com.dus.ISchemaRepository;
import com.dus.ISession;
import com.dus.base.EntityID;
import com.dus.base.IEntity;
import com.dus.base.builder.IBuilder;
import com.dus.base.finder.FetchOptions;
import com.dus.base.finder.IFinder;
import com.dus.base.schema.SEntity;
import com.dus.base.schema.SProperty;
import com.dus.context.Context;
import com.dus.impl.entity.EntityProxyHandler;
import com.dus.impl.transaction.Transaction;
import com.dus.spi.container.IStore;
import com.dus.spi.router.IQueryRouter;
import com.dus.spi.router.ITransactionRouter;

public class Session implements ISession {
	
	private final ISchemaRepository repository;
	private final IStore store;
	
	private final ITransactionRouter tRouter;
	private final IQueryRouter qRouter;
	
	public Session(ISchemaRepository repository, IStore store, ITransactionRouter tRouter, IQueryRouter qRouter) {
		this.repository = repository;
		this.store = store;
		
		this.tRouter = tRouter;
		this.qRouter = qRouter;
		
		Context.getData().setSession(this);
		Context.getData().setContextObject(Transaction.class, new Transaction(this));
	}
	
	public IStore getStore() {return store;}
	
	public ITransactionRouter getTransactionRouter() {return tRouter;}
	public IQueryRouter getQueryRouter() {return qRouter;}
	
	@Override
	public <T extends IEntity> T create(Class<T> type) {
		return create(type, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends IEntity> T create(Class<T> type, IBuilder builder) {
		SEntity schema = repository.getSchemaFor(type);
		
		EntityID id = new EntityID(schema, "TMP-"+UUID.randomUUID().toString());
		EntityProxyHandler handler = new EntityProxyHandler(id, store);
		
		Transaction tx = Context.getData().getContextObject(Transaction.class);
		tx.newEntity(id);
		
		if(builder != null)
			for(Field field: builder.getClass().getFields()) {
				SProperty property = schema.getPropertyByName(field.getName());
				if(property == null) throw new RuntimeException("Property ("+ field.getName() +") not available in: "+ schema.getType().getName());
				
				Object value = ReflectionHelper.getValue(field, builder);
				handler.setPropertyValue(property, value);
			}
		
		handler.setCreated();
		return (T) handler.getEntity();
	}

	@Override
	public <F extends IFinder<? extends IEntity>> F find(Class<F> finder) {
		return find(finder, null);
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public <F extends IFinder<? extends IEntity>> F find(Class<F> finder, FetchOptions fOptions) {
		FinderProxyHandler handler = new FinderProxyHandler(this, finder, fOptions);
		
		return (F) handler.getFinder();
	}

	@Override
	public void commit() {
		Transaction tx = Context.getData().getContextObject(Transaction.class);
		if(tx.commit())
			Context.getData().setContextObject(Transaction.class, new Transaction(this));
	}

	@Override
	public void revert() {
		Transaction tx = Context.getData().getContextObject(Transaction.class);
		tx.revert();	//revert all changes in the model
		Context.getData().setContextObject(Transaction.class, new Transaction(this));
	}

}
