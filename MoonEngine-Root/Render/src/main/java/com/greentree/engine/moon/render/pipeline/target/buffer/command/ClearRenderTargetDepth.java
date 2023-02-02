package com.greentree.engine.moon.render.pipeline.target.buffer.command;

import com.greentree.engine.moon.render.pipeline.RenderLibrary;

public record ClearRenderTargetDepth(RenderLibrary library, float depth) implements TargetCommand {
	
	@Override
	public void run() {
		library.clearRenderTargetDepth(depth);
	}
	
}
