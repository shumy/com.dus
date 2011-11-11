package com.dus.spi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class Tree<K1, K2, V3> implements ITree<K1, K2, V3> {
	private HashMap<K1, HashMap<K2, V3>> cache = new HashMap<K1, HashMap<K2, V3>>();
	
	@Override
	public V3 get(K1 k1, K2 k2) {
		HashMap<K2, V3> level = cache.get(k1);
		if(level != null) return level.get(k2);
		return null;
	}
	
	@Override
	public boolean containsKey(K1 k1) {
		return keySetLevel1().contains(k1);
	}
	
	@Override
	public boolean containsKey(K1 k1, K2 k2) {
		return keySetLevel2(k1).contains(k2);
	}
	
	@Override
	public Set<K1> keySetLevel1() {
		return cache.keySet();
	}
	
	@Override
	public Set<K2> keySetLevel2(K1 k1) {
		HashMap<K2, V3> level = cache.get(k1);
		if(level == null) return Collections.emptySet();
		return level.keySet();
	}
	
	public void set(K1 k1, K2 k2, V3 v3) {
		HashMap<K2, V3> level = cache.get(k1);
		if(level == null) {
			level = new HashMap<K2, V3>();
			cache.put(k1, level);
		}
		level.put(k2, v3);
	}
	
	public void remove(K1 k1) {
		cache.remove(k1);
	}
	
	public void remove(K1 k1, K2 k2) {
		HashMap<K2, V3> level = cache.get(k1);
		if(level != null) {
			level.remove(k2);
			if(level.isEmpty()) cache.remove(k1);
		}
	}
	
	public void clear() {cache.clear();}
}
