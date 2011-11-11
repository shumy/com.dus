package com.dus.base;



public interface IEntity extends INotifiable, IReflected {	
	EntityID getId();
	
	boolean validate();
}
