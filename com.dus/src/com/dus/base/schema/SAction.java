package com.dus.base.schema;


public interface SAction extends SFeature {
	
	//default is enabled
	boolean isEnabled();
	void setEnabled(boolean isEnabled);
}
