package com.dus;

import com.dus.base.IEntity;
import com.dus.base.schema.SEntity;

public interface ISchemaRepository {
	void register(Class<? extends IEntity> type);
	
	SEntity getSchemaFor(Class<? extends IEntity> type);
	SEntity getSchemaFor(String typeName);
}
