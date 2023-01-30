package com.greentree.commons.assets.value;

import java.util.function.Consumer;

import com.greentree.commons.assets.value.provider.ValueProvider;


public abstract class AbstractRefCountValue<T> implements Value<T> {
	
	private static final long serialVersionUID = 1L;
	
	private ValueProvider<T> rawProvider;
	private int refCount;
	
	private long lastRawGet = Long.MIN_VALUE;
	
	@Override
	public T get() {
		if(rawProvider != null) {
			return rawProvider.get();
		}
		return Value.super.get();
	}
	
	@Deprecated
	@Override
	public boolean isNull() {
		if(rawProvider != null) {
			return rawProvider.isNull();
		}
		return Value.super.isNull();
	}
	
	@Override
	public final int characteristics() {
		if(rawProvider != null) {
			return rawProvider.characteristics();
		}
		return rawCharacteristics();
	}
	
	protected int rawCharacteristics() {
		return 0;
	}
	
	@Override
	public final ValueProvider<T> openProvider() {
		return new Provider();
	}
	
	private void begin() {
		if(rawProvider == null)
			rawProvider = openRawProvider();
		refCount++;
	}
	
	private void end() {
		refCount--;
		if(refCount == 0) {
			rawProvider.close();
			rawProvider = null;
			lastRawGet = Long.MIN_VALUE;
			clear();
		}
	}
	
	private T raw_get() {
		return rawProvider.get();
	}
	
	private boolean raw_isChenge() {
		if(rawProvider.isChenge()) {
			lastRawGet++;
			return true;
		}
		return false;
	}
	
	protected void clear() {
	}
	
	protected abstract ValueProvider<T> openRawProvider();
	
	private final class Provider implements ValueProvider<T> {
		
		boolean isClose;
		
		private long lastGet = lastRawGet;
		
		public Provider() {
			isClose = false;
			begin();
		}
		
		@Override
		public T getNotChenge() {
			return raw_get();
		}
		
		@Override
		public int characteristics() {
			return rawProvider.characteristics();
		}
		
		@Override
		public void close() {
			if(isClose)
				throw new UnsupportedOperationException("double closing");
			isClose = true;
			end();
		}
		
		@Override
		public boolean tryGet(Consumer<? super T> action) {
			if(lastGet == lastRawGet)
				return false;
			return rawProvider.tryGet(v-> {
				lastRawGet++;
				lastGet = lastRawGet;
				action.accept(v);
			});
		}
		
		@Override
		public T get() {
			lastGet = lastRawGet;
			return raw_get();
		}
		
		@Override
		public boolean isChenge() {
			return (lastGet < lastRawGet) || raw_isChenge();
		}
		
		@Override
		public String toString() {
			return "RefCountProvider [" + rawProvider + "]";
		}
		
	}
	
}
