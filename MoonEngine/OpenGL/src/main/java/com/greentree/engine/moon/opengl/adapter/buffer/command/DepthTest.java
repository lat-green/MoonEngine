package com.greentree.engine.moon.opengl.adapter.buffer.command;

import static org.lwjgl.opengl.GL11.*;

import java.util.Objects;

public record DepthTest(TargetCommand command) implements TargetCommand {
	
	
	public DepthTest {
		Objects.requireNonNull(command);
	}
	
	@Override
	public void run() {
		glEnable(GL_DEPTH_TEST);
		command.run();
		glDisable(GL_DEPTH_TEST);
	}
	
	@Override
	public TargetCommand merge(TargetCommand other) {
		if(equals(other))
			return this;
		if(other instanceof DepthTest c) {
			final var m = command.merge(c.command);
			if(m != null)
				return new DepthTest(m);
		}
		return TargetCommand.super.merge(other);
	}
	
}
