package test.domain;

import com.dus.base.Entity;

@Entity
public interface Person extends User {
	//PROPERTIES--------------------------------------------------------------------------------
	String getName();
	Person setName(String name);
	
	String getAddress();
	Person setAddress(String address);
	
	//BUILDER----------------------------------------------------------------------------------
	public class Builder extends User.Builder {
		
		public Builder(String login) {
			super(login);
		}
	}
}
