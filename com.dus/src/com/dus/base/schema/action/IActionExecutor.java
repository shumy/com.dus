package com.dus.base.schema.action;

import com.dus.base.IEntity;

public interface IActionExecutor {
	Object execute(IEntity entity, Object ...parameters);
}
