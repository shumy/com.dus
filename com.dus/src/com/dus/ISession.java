package com.dus;

import com.dus.base.IEntity;
import com.dus.base.builder.IBuilder;
import com.dus.base.finder.FetchConfig;
import com.dus.base.finder.IFinder;

public interface ISession {
		
	<T extends IEntity> T create(Class<T> type);
	<T extends IEntity> T create(Class<T> type, IBuilder builder);
	
	//<T extends IEntity> void reload(T entity);
	
	<F extends IFinder<?>> F find(Class<F> finder);
	<F extends IFinder<?>> F find(Class<F> finder, FetchConfig fConfig);
	
	void commit();
	void revert();
}
