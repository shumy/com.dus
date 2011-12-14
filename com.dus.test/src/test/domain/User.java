package test.domain;

import com.dus.base.ClientActions;
import com.dus.base.IEntity;
import com.dus.base.IProperty;
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
	
	//TODO: is this a good option for action definition?
	@ClientActions
	class ActionImpl {
		
		IProperty<String> password;		//access to internal properties...
		
		boolean verifyPassword(String password) {
			if(password == null || password.isEmpty()) return false;
			
			return this.password.get().equals(password);
		}
	}
	
	
	
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
