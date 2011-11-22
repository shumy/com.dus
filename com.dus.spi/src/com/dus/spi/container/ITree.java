package com.dus.spi.container;

import java.util.Set;

public interface ITree<K1, K2, V3> {
	V3 get(K1 k1, K2 k2);
	
	boolean containsKey(K1 k1);
	boolean containsKey(K1 k1, K2 k2);
	
	Set<K1> keySetLevel1();
	Set<K2> keySetLevel2(K1 k1);
}
