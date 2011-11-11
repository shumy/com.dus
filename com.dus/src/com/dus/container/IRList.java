package com.dus.container;

public interface IRList<T> extends Iterable<T> {
	boolean contains(T item);
	
	int size();
}
