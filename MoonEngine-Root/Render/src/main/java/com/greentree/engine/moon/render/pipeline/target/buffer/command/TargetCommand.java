package com.greentree.engine.moon.render.pipeline.target.buffer.command;

public interface TargetCommand {
	
	void run();
	
	default TargetCommand merge(TargetCommand command) {
		return null;
	}
	
}
