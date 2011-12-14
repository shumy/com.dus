package com.dus.base;

import com.dus.base.schema.SAction;
import com.dus.base.schema.SProperty;

public interface IReflected {
	
	<W> W rGet(SProperty property);
	void rSet(SProperty property, Object value);
	void rUnset(SProperty property);
	
	<W> W rInvoke(SAction action, Object ...parameters);
}
