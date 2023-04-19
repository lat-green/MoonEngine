package com.greentree.engine.moon.signals.device;


public enum EventState {

	ON_ENABLE(){
		
		@Override
		public boolean is(boolean current, boolean next) {
			return !current && next;
		}
	},
	ON_DISABLE(){
		
		@Override
		public boolean is(boolean current, boolean next) {
			return current && !next;
		}
	},
	DISABLE() {
		
		@Override
		public boolean is(boolean current, boolean next) {
			return !current && !next;
		}
	},
	;
	
	public abstract boolean is(boolean current, boolean next);
	
}
