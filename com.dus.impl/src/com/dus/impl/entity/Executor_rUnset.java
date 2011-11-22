package com.dus.impl.entity;

import com.dus.base.notification.Notification;
import com.dus.base.notification.Notification.Type;
import com.dus.base.schema.SProperty;
import com.dus.context.Context;
import com.dus.impl.transaction.Transaction;
import com.dus.spi.container.IStore;

public class Executor_rUnset implements IMethodExecutor {

	@Override
	public Object execute(EntityProxyHandler handler, Object... parameters) {
		SProperty property = (SProperty) parameters[0];
		if(handler.isCreated() && !property.isWrite()) throw new RuntimeException("Property ("+ property.getName() +") from ("+ handler.getId().schema.getType().getName() +") is not writable!");
		
		IStore store = handler.getStore();
		
		Object oldValue = store.getProperty(handler.getId(), property);
		
		store.setProperty(handler.getId(), property, null);
		
		Notification notification = new Notification(handler.getId(), property, Type.SET, oldValue, null, -1);
		
		Transaction tx = Context.getData().getContextObject(Transaction.class);
		tx.addChange(notification);
		handler.notify(notification);
		
		return null;
	}

}
