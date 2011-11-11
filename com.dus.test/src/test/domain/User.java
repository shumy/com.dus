package test.domain;

import com.dus.base.IEntity;
import com.dus.base.builder.IBuilder;
import com.dus.base.finder.IFinder;
import com.dus.container.IList;

public interface User extends IEntity {
		
	//PROPERTIES--------------------------------------------------------------------------------
	String getLogin();
	
	User setPassword(String password);
	
	IList<Group> getGroups();
	
	//ACTIONS-----------------------------------------------------------------------------------
	boolean verifyPassword(String password);
	
	//BUILDER----------------------------------------------------------------------------------
	public class Builder implements IBuilder {
		public final String login;
		
		public Builder(String login) {
			this.login = login;
		}
	}
	
	//FINDER------------------------------------------------------------------------------------
	interface Find extends IFinder<User> {	//implementation on server
		User byName(String name);
	}
}
