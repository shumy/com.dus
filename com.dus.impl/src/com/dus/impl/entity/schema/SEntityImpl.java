package com.dus.impl.entity.schema;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.dus.base.IEntity;
import com.dus.base.schema.SEntity;
import com.dus.base.schema.SProperty;
import com.dus.base.schema.action.SAction;
import com.dus.container.IRList;

public class SEntityImpl implements SEntity {
	private final Class<? extends IEntity> type;
	
	private final Map<String, SProperty> properties = new HashMap<String, SProperty>();
	private final IRList<SProperty> propList = new IRList<SProperty>() {
		@Override
		public Iterator<SProperty> iterator() {
			return properties.values().iterator();
		}
		
		@Override
		public boolean contains(SProperty item) {
			return properties.containsKey(item.getName());
		}
		
		@Override
		public int size() {
			return properties.keySet().size();
		}
	};
	
	private final Map<String, SAction> actions = new HashMap<String, SAction>();
	private final IRList<SAction> actionList = new IRList<SAction>() {
		@Override
		public Iterator<SAction> iterator() {
			return actions.values().iterator();
		}
		
		@Override
		public boolean contains(SAction item) {
			return actions.keySet().contains(item.getName());
		}
		
		@Override
		public int size() {
			return actions.keySet().size();
		}
	};
	
	public SEntityImpl(Class<? extends IEntity> type) {
		this.type = type;
	}
	
	public void addComponent(SEntity component) {
		for(SProperty prop: component.getProperties())
			properties.put(prop.getName(), prop);
		
		for(SAction action: component.getActions())
			actions.put(action.getName(), action);
	}
	
	public void addProperty(SPropertyImpl property) {
		properties.put(property.getName(), property);
	}
	
	public void addAction(SActionImpl action) {
		actions.put(action.getName(), action);
	}
	
	@Override
	public Class<? extends IEntity> getType() {return type;}

	@Override
	public IRList<SProperty> getProperties() {return propList;}

	@Override
	public SProperty getPropertyByName(String name) {return properties.get(name);}

	@Override
	public IRList<SAction> getActions() {return actionList;}

	@Override
	public SAction getActionByName(String name) {return actions.get(name);}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("SEntity: ");
		sb.append(type.getName());
		sb.append("\n");
		
		for(SProperty prop: propList) {
			sb.append("  P ");
			sb.append(prop.toString());
			sb.append("\n");
		}
		
		for(SAction action: actionList) {
			sb.append("  A ");
			sb.append(action.toString());
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
