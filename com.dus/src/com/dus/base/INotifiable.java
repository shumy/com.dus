package com.dus.base;

import com.dus.base.notification.INotificationListener;
import com.dus.container.IList;

public interface INotifiable {
	
	//default is disabled
	boolean isNotificationsEnabled();		
	void setNofiticationsEnabled(boolean enabled);
	
	IList<INotificationListener> getListeners();
}
