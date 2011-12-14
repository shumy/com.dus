package com.dus.container;

public interface IList<T> extends IRList<T> {
	void add(T item);	
	void remove(T item);
}
