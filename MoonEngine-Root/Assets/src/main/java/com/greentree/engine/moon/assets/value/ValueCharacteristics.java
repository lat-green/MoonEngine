package com.greentree.engine.moon.assets.value;

import java.util.ArrayList;

public interface ValueCharacteristics<T> {
	
	int NOT_NULL = 0x00000001;
	int BLANCK_CLOSE = 0x00000002;
	int DISTINCT_CHANGE = 0x00000004;
	int CECHED = 0x00000008;
	int CONST = 0x00000010 | CECHED;
	@Deprecated
	int ASYNC = 0x00000020;
	@Deprecated
	int NOT_ASYNC = 0x00000030;
	
	
	
	int characteristics();
	
	default boolean hasCharacteristics(int characteristics) {
		return (characteristics() & characteristics) == characteristics;
	}
	
	static String toString(int characteristics) {
		final var result = new ArrayList<>(5);
		if((characteristics & CONST) != 0)
			result.add("CONST");
		if((characteristics & CECHED) != 0)
			result.add("CECHED");
		if((characteristics & DISTINCT_CHANGE) != 0)
			result.add("DISTINCT_CHANGE");
		if((characteristics & BLANCK_CLOSE) != 0)
			result.add("BLANCK_CLOSE");
		if((characteristics & NOT_NULL) != 0)
			result.add("NOT_NULL");
		return result.toString();
	}
	
}
