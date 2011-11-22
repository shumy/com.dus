package com.dus.impl.transaction;

import java.util.Stack;

import com.dus.base.EntityID;
import com.dus.base.notification.Notification;
import com.dus.base.schema.SProperty;
import com.dus.impl.Session;
import com.dus.impl.transaction.TransactionData.AddRemoveReport;
import com.dus.spi.container.IStore;
import com.dus.spi.transaction.ITransactionResponse;

public class Transaction {
	
	private final Session session;
	
	private final Stack<Notification> changeStack = new Stack<Notification>();
	private final TransactionData changeResume = new TransactionData();
	
	//--------------------------------------------------------------------------------------------------------------------------------
	public Transaction(Session session) {
		this.session = session;
	}
	
	public TransactionData getChangeResume() {return changeResume;}
	
	public boolean commit() {
		ITransactionResponse txResponse = session.getTransactionRouter().commit(changeResume);
		
		//update tmp-id's with values from server...
		/*Map<EntityID, EntityID> idMap = txResponse.getIdMap();
		for(EntityID tmpId: idMap.keySet()) {
			Entity tmpEntity = newEntities.get(tmpId);
			if(tmpEntity != null) {
				session.deactivateAdapter(tmpEntity);
				tmpEntity.setId(idMap.get(tmpId).typeId);
				session.activateAdapter(tmpEntity);
				
				entities.put(idMap.get(tmpId), tmpEntity);
			}
				
		}*/
		
		//TODO: update entity fields with data from server...
		
		return true;
	}
	
	public void newEntity(EntityID id) {
		changeResume.newEntity(id);
	}
	
	public void deleteEntity(EntityID id) {
		changeResume.deleteEntity(id);
	}
	
	public void addChange(Notification notification) {
		
		switch (notification.type) {
			case SET:
				changeResume.addProperty(notification.entityId, notification.property, notification.newValue);
				break;
			
			case UNSET:
				changeResume.removeProperty(notification.entityId, notification.property);
				break;
				
			case ADD:
				addReport(notification.entityId, notification.property, notification.newValue);
				break;
				
			case REMOVE:
				removeReport(notification.entityId, notification.property, notification.oldValue);
				break;
		}
		
		changeStack.push(notification);
	}
	
	public void revert() {
		while(!changeStack.isEmpty()) {
			Notification ch = changeStack.pop();
			IStore store = session.getStore();
			
			switch (ch.type) {
				case SET:
				case UNSET:
					store.setProperty(ch.entityId, ch.property, ch.oldValue);
					break;
				
				case ADD:
					store.removeReference(ch.entityId, ch.property, ch.listIndex);
					break;
					
				case REMOVE:
					store.addReference(ch.entityId, ch.property, (EntityID)ch.oldValue, ch.listIndex);
					break;
			}
		}
	}

	private void addReport(EntityID entityId, SProperty property, Object value) {
		AddRemoveReport arReport = changeResume.getOrCreateReport(entityId, property);
		
		EntityID refEntityId = ((EntityID)value);
		arReport.reportAdd(refEntityId);
	}
	
	private void removeReport(EntityID entityId, SProperty property, Object value) {
		AddRemoveReport arReport = changeResume.getOrCreateReport(entityId, property);
		
		EntityID refEntityId = ((EntityID)value);
		arReport.reportRemove(refEntityId);
	}
}
