package com.dus.impl.entity;

import com.dus.base.IEntity;
import com.dus.base.notification.Notification;
import com.dus.base.notification.Notification.Type;
import com.dus.base.schema.SProperty;
import com.dus.context.Context;
import com.dus.impl.transaction.Transaction;
import com.dus.spi.container.IStore;

public class Executor_rSet implements IMethodExecutor {

	@Override
	public Object execute(EntityProxyHandler handler, Object... parameters) {
		SProperty property = (SProperty) parameters[0];
		
		IStore store = handler.getStore();
		Object newRawValue = parameters[1];
		
		Object newValue = null;
		Object oldValue = store.getProperty(handler.getId(), property);
		
		if(newRawValue instanceof IEntity)
			newValue = ((IEntity) newRawValue).getId();
		else
			newValue = newRawValue;
		
		store.setProperty(handler.getId(), property, newValue);
		
		Notification notification = new Notification(handler.getId(), property, Type.SET, oldValue, newValue, -1);
		
		Transaction tx = Context.getData().getContextObject(Transaction.class);
		tx.addChange(notification);
		handler.notify(notification);
		
		return null;
	}

}
