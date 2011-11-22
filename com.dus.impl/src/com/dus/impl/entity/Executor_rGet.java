package com.dus.impl.entity;

import java.util.Iterator;
import java.util.List;

import com.dus.base.EntityID;
import com.dus.base.IEntity;
import com.dus.base.notification.Notification;
import com.dus.base.notification.Notification.Type;
import com.dus.base.schema.SProperty;
import com.dus.container.IList;
import com.dus.context.Context;
import com.dus.impl.transaction.Transaction;
import com.dus.spi.container.IStore;

public class Executor_rGet implements IMethodExecutor {
	private static class ReferenceList implements IList<IEntity> {
		private class RefIterator implements Iterator<IEntity> {
			private int index = 0;
			private final EntityID[] refArray;
			
			public RefIterator() {
				List<EntityID> refList = store.getReferenceList(handler.getId(), property);
				refArray = refList.toArray(new EntityID[refList.size()]);	//make a safe copy of reference list
			}
					
			@Override
			public boolean hasNext() {
				return index < refArray.length - 1;
			}

			@Override
			public IEntity next() {
				return store.getEntity(refArray[index++]);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Removes not allowed in Reference List Iterator!");
			}
			
			@Override
			public String toString() {
				return "[ref-iterator]";
			}
		}
		
		private final EntityProxyHandler handler;
		private final SProperty property;
		private final IStore store;
		
		ReferenceList(EntityProxyHandler handler, SProperty property, IStore store) {
			this.handler = handler;
			this.property = property;
			this.store = store;
		}
		
		@Override
		public boolean contains(IEntity item) {
			return store.containsReference(handler.getId(), property, item.getId());
		}

		@Override
		public Iterator<IEntity> iterator() {
			return new RefIterator();
		}

		@Override
		public void add(IEntity item) {
			EntityID ref = item.getId();
			
			int index = store.addReference(handler.getId(), property, ref);
			Notification notification = new Notification(handler.getId(), property, Type.ADD, null, ref, index);
			notify(notification);
		}

		@Override
		public void remove(IEntity item) {
			EntityID ref = item.getId();
			
			int index = store.removeReference(handler.getId(), property, ref);
			if(index != -1) {	//is really removed?
				Notification notification = new Notification(handler.getId(), property, Type.REMOVE, ref, null, index);
				notify(notification);
			}
		}
		
		@Override
		public int size() {
			return store.getSizeOfRefList(handler.getId(), property);
		}
		
		private void notify(Notification notification) {
			Transaction tx = Context.getData().getContextObject(Transaction.class);
			tx.addChange(notification);
			handler.notify(notification);
		}
		
		@Override
		public String toString() {
			return "[ref-list]";
		}
	}
	
	@Override
	public Object execute(EntityProxyHandler handler, Object... parameters) {
		SProperty property = (SProperty) parameters[0];
		if(handler.isCreated() && !property.isRead()) throw new RuntimeException("Property ("+ property.getName() +") from ("+ handler.getId().schema.getType().getName() +") is not readable!");
		
		IStore store = handler.getStore();
		if(property.isMany()) { //is a reference list ?
			return new ReferenceList(handler, property, store);
		} else {
			Object value = store.getProperty(handler.getId(), property);
			if(value instanceof EntityID) {
				return store.getEntity((EntityID)value);
			} else
				return value;
		}
	}
	
}
