package com.dus.spi.container;

import java.util.HashSet;
import java.util.Iterator;

import com.dus.container.IList;

public class BasicList<T> implements IList<T> {
	private final HashSet<T> items = new HashSet<T>();
	
	@Override
	public boolean contains(T item) {
		return items.contains(item);
	}

	@Override
	public int size() {
		return items.size();
	}

	@Override
	public Iterator<T> iterator() {
		return items.iterator();
	}

	@Override
	public void add(T item) {
		items.add(item);
	}

	@Override
	public void remove(T item) {
		items.remove(item);
	}

}
