package com.greentree.engine.moon.render.pipeline.target.buffer.command;

import java.util.Objects;

import com.greentree.engine.moon.render.pipeline.RenderLibrary;

public record DepthTest(RenderLibrary library, TargetCommand command) implements TargetCommand {
	
	
	public DepthTest {
		Objects.requireNonNull(library);
		Objects.requireNonNull(command);
	}
	
	@Override
	public void run() {
		library.enableDepthTest();
		command.run();
		library.disableDepthTest();
	}
	
	@Override
	public TargetCommand merge(TargetCommand other) {
		if(equals(other))
			return this;
		if(other instanceof DepthTest c) {
			if(c.library.equals(library)) {
				final var m = command.merge(c.command);
				if(m != null)
					return new DepthTest(library, m);
			}
		}
		return TargetCommand.super.merge(other);
	}
	
}
