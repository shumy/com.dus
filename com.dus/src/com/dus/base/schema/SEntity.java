package com.dus.base.schema;

import com.dus.base.schema.action.SAction;
import com.dus.container.IRList;

public interface SEntity {
	Class<?> getType();
	
	IRList<SProperty> getProperties();
	SProperty getPropertyByName(String name);
	
	IRList<SAction> getActions();
	SAction getActionByName(String name);
}
