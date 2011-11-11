package com.dus.base.schema.action;

import com.dus.base.schema.SFeature;

public interface SAction extends SFeature {
	
	//default is enabled
	boolean isEnabled();
	void setEnabled(boolean isEnabled);
	
	IActionExecutor getExecutor();
	void setExecutor(IActionExecutor executor);
}
