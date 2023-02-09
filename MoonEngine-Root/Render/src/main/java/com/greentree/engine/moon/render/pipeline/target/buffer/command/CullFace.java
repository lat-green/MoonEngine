package com.greentree.engine.moon.render.pipeline.target.buffer.command;

import java.util.Objects;

import com.greentree.engine.moon.render.pipeline.RenderLibrary;

public record CullFace(RenderLibrary library, TargetCommand command) implements TargetCommand {
	
	
	public CullFace {
		Objects.requireNonNull(library);
		Objects.requireNonNull(command);
	}
	
	@Override
	public void run() {
		library.enableCullFace();
		command.run();
		library.disableCullFace();
	}
	
	@Override
	public TargetCommand merge(TargetCommand other) {
		if(equals(other))
			return this;
		if(other instanceof CullFace c) {
			if(c.library.equals(library)) {
				final var m = command.merge(c.command);
				if(m != null)
					return new CullFace(library, m);
			}
		}
		return TargetCommand.super.merge(other);
	}
	
}
