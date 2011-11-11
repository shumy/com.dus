package com.dus.base.finder;

import com.dus.base.IEntity;


public interface IFinder<T extends IEntity> {
	T byId(String id);
	T byExample(T example);
}
