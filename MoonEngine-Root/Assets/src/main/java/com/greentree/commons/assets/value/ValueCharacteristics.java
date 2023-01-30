package com.greentree.commons.assets.value;


public interface ValueCharacteristics<T> {
	
	int NOT_NULL = 0x00000001;
	int BLANCK_CLOSE = 0x00000002;
	int DISTINCT_CHANGE = 0x00000004;
	int CONST = 0x00000008;
	int CECHED = 0x00000010;
	
	
	int characteristics();
	
	default boolean hasCharacteristics(int characteristics) {
		return (characteristics() & characteristics) == characteristics;
	}
	
	default boolean hasCharacteristicConst() {
		return hasCharacteristics(CONST);
	}
}
