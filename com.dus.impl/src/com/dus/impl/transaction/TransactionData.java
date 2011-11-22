package com.dus.impl.transaction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.dus.base.EntityID;
import com.dus.base.schema.SProperty;
import com.dus.spi.container.ITree;
import com.dus.spi.container.Tree;
import com.dus.spi.transaction.ITransactionRequest;

public final class TransactionData implements ITransactionRequest {
	
	public static class AddRemoveReport implements ITransactionRequest.IAddRemoveReport {
		//<entity-id>
		private final Set<EntityID> addEntities = new HashSet<EntityID>();
		
		//<entity-id>
		private final Set<EntityID> removeEntities = new HashSet<EntityID>();
		
		@Override
		public Set<EntityID> getAddEntities() {return Collections.unmodifiableSet(addEntities);}
		
		@Override
		public Set<EntityID> getRemoveEntities() {return Collections.unmodifiableSet(removeEntities);}
		
		public void reportAdd(EntityID id) {
			removeEntities.remove(id);
			addEntities.add(id);
		}
		
		public void reportRemove(EntityID id) {
			addEntities.remove(id);
			removeEntities.add(id);
		}
	}
	
	private final String id = UUID.randomUUID().toString();
	
	//<entity-id>
	private final Set<EntityID> newEntities = new HashSet<EntityID>();
	
	//<entity-id>
	private final Set<EntityID> deleteEntities = new HashSet<EntityID>();
	
	//<entity-id> <property-name> <supported-value>
	private final Tree<EntityID, SProperty, Object> properties = new Tree<EntityID, SProperty, Object>();
	
	//<entity-id> <property-name> <add-remove-report>
	private final Tree<EntityID, SProperty, IAddRemoveReport> references = new Tree<EntityID, SProperty, IAddRemoveReport>();
	
	@Override
	public String getId() {return id;}
	
	@Override
	public Set<EntityID> getNewEntities() {return Collections.unmodifiableSet(newEntities);}
	
	@Override
	public Set<EntityID> getDeleteEntities() {return Collections.unmodifiableSet(deleteEntities);}
	
	@Override
	public ITree<EntityID, SProperty, Object> getProperties() {return properties;}
	
	@Override
	public ITree<EntityID, SProperty, IAddRemoveReport> getReferences() {return references;}
	
	public void newEntity(EntityID id) {
		deleteEntities.remove(id);
		newEntities.add(id);
	}
	
	public void deleteEntity(EntityID id) {
		//delete all change history:
		properties.remove(id);
		references.remove(id);
		
		if(newEntities.contains(id))
			newEntities.remove(id);
		else
			deleteEntities.add(id);
	}
	
	public void addProperty(EntityID entityId, SProperty property, Object value) {
		if(value != null)
			properties.set(entityId, property, value);
		else
			properties.remove(entityId, property);
	}
	
	public void removeProperty(EntityID entityId, SProperty property) {
		properties.remove(entityId, property);
	}
	
	public AddRemoveReport getOrCreateReport(EntityID entityId, SProperty property) {
		AddRemoveReport arReport = (AddRemoveReport) references.get(entityId, property);
		if(arReport == null) {
			arReport = new AddRemoveReport();
			references.set(entityId, property, arReport);
		}
		return arReport;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("TRANSACTION: ");
		sb.append(id);
		sb.append("\n  DELETE ENTITIES: [");
		if(!deleteEntities.isEmpty()) {
			for(EntityID id: deleteEntities) {
				sb.append(id);
				sb.append(", ");
			}
			sb.delete(sb.length()-2, sb.length());
		}
		sb.append("]\n");
		
		sb.append("  NEW ENTITIES: [");
		if(!newEntities.isEmpty()) {
			for(EntityID id: newEntities) {
				sb.append(id);
				sb.append(", ");
			}
			sb.delete(sb.length()-2, sb.length());
		}
		sb.append("]\n");
		
		sb.append("  PROPERTIES: \n");
		for(EntityID id: properties.keySetLevel1()) {
			sb.append("\tENTITY "); 
			sb.append(id);
			sb.append(": {");
			if(!properties.keySetLevel2(id).isEmpty()) {
				for(SProperty prop: properties.keySetLevel2(id)) {
					sb.append(prop.getName());
					sb.append("=");
					sb.append(properties.get(id, prop));
					sb.append(", ");
				}
				sb.delete(sb.length()-2, sb.length());
			}
			sb.append("}\n");
		}
		
		sb.append("  REFERENCES: \n");
		for(EntityID id: references.keySetLevel1()) {
			sb.append("\tENTITY "); 
			sb.append(id);
			sb.append(": {");
			for(SProperty ref: references.keySetLevel2(id)) {
				sb.append(ref.getName());
				sb.append("(add=[");
				IAddRemoveReport arReport = references.get(id, ref);
				if(!arReport.getAddEntities().isEmpty()) {
					for(EntityID idToAdd: arReport.getAddEntities()) {
						sb.append(idToAdd);
						sb.append(", ");
					}
					sb.delete(sb.length()-2, sb.length());
				}
				sb.append("], remove=[");
				if(!arReport.getRemoveEntities().isEmpty()) {
					for(EntityID idToRemove: arReport.getRemoveEntities()) {
						sb.append(idToRemove);
						sb.append(", ");
					}
					sb.delete(sb.length()-2, sb.length());
				}
				sb.append("]), ");
			}
			sb.delete(sb.length()-2, sb.length());
			
			sb.append("}\n");
		}
		
		
		return sb.toString();
	}
}
