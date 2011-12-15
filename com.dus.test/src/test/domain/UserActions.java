package test.domain;

import com.dus.base.IProperty;

public class UserActions {
	IProperty<String> password;		//access to internal properties...
	
	boolean verifyPassword(String password) {
		if(password == null || password.isEmpty()) return false;
		
		return this.password.get().equals(password);
	}
}
