package com.greentree.engine.moon.opengl.adapter.buffer.command;

import static org.lwjgl.opengl.GL11.*;

import java.util.Objects;

public record CullFace(TargetCommand command) implements TargetCommand {
	
	
	public CullFace {
		Objects.requireNonNull(command);
	}
	
	@Override
	public void run() {
		glEnable(GL_CULL_FACE);
		command.run();
		glDisable(GL_CULL_FACE);
	}
	
	@Override
	public TargetCommand merge(TargetCommand other) {
		if(equals(other))
			return this;
		if(other instanceof CullFace c) {
			final var m = command.merge(c.command);
			if(m != null)
				return new CullFace(m);
		}

		return TargetCommand.super.merge(other);
	}
	
}
