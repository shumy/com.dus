package com.dus.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


import com.dus.base.EntityID;
import com.dus.base.IEntity;
import com.dus.base.finder.FetchOptions;
import com.dus.base.finder.IFinder;
import com.dus.base.schema.SProperty;
import com.dus.impl.query.QueryData;
import com.dus.spi.container.BasicList;
import com.dus.spi.container.ITree;
import com.dus.spi.query.IQueryResponse;

public class FinderProxyHandler implements InvocationHandler {
	private final Session session;
	
	private final IFinder<?> finder;
	private final FetchOptions fOptions;
	
	public FinderProxyHandler(Session session, Class<? extends IFinder<? extends IEntity>> i_nterface, FetchOptions fOptions) {
		this.session = session;
		this.fOptions = fOptions;
		this.finder = ReflectionHelper.createProxy(i_nterface, this);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] parameters) throws Throwable {
		QueryData qRequest = new QueryData(finder.getClass().getName(), method.getName(), fOptions, parameters);
		IQueryResponse qResponse = session.getQueryRouter().execute(qRequest);
		
		if(qResponse.isSingleResult()) {
			EntityID entityId = qResponse.getSingleResult();
			return getOrCreateFromStore(entityId, qResponse.getValues());
		} else {
			BasicList<IEntity> results = new BasicList<IEntity>();
			for(EntityID entityId: qResponse.getResults()) {
				IEntity entity = getOrCreateFromStore(entityId, qResponse.getValues());
				results.add(entity);
			}
			
			return results;
		}
	}
	
	private IEntity getOrCreateFromStore(EntityID entityId, ITree<EntityID, SProperty, Object> values) {
		IEntity entity = session.getStore().getEntity(entityId);
		if(entity == null) {
			//TODO: create in cache...
		}
		
		//TODO: update fetched values...
			
		return entity;
	}
	
	public IFinder<? extends IEntity> getFinder() {return finder;}

}
