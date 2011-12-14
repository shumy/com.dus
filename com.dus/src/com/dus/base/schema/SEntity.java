package com.dus.base.schema;

import com.dus.base.IEntity;
import com.dus.container.IRList;

public interface SEntity {
	Class<? extends IEntity> getType();
	
	IRList<SProperty> getProperties();
	SProperty getPropertyByName(String name);
	
	IRList<SAction> getActions();
	SAction getActionByName(String name);
}
