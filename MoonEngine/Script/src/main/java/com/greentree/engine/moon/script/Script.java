package com.greentree.engine.moon.script;


public interface Script extends AutoCloseable {
	
	void clear();
	@Override
	void close();
	
	void delete(String name);
	
	@SuppressWarnings("unchecked")
	default <T> T get(Class<T> cls, String name) {
		return (T) get(name);
	}
	
	Object get(String name);
	void run();
	
	void set(String name, Object value);
	
	default void setConst(String name, Object value) {
		set(name, value);
	}
	
}
