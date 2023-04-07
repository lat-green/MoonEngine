package com.greentree.engine.moon.opengl.adapter.buffer.command;

public interface TargetCommand {
	
	void run();
	
	default TargetCommand merge(TargetCommand command) {
		if(command == this)
			return this;
		return null;
	}
	
}
