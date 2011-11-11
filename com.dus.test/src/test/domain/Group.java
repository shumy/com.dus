package test.domain;

import com.dus.base.IEntity;
import com.dus.base.builder.IBuilder;
import com.dus.base.finder.IFinder;

public interface Group extends IEntity {

	//PROPERTIES--------------------------------------------------------------------------------
	String getName();
	Group setName(String name);
	
	//BUILDER----------------------------------------------------------------------------------
	public class Builder implements IBuilder {
		public final String name;
		
		public Builder(String name) {
			this.name = name;
		}
	}
	
	//FINDER------------------------------------------------------------------------------------
	interface Find extends IFinder<User> {
	}
}
