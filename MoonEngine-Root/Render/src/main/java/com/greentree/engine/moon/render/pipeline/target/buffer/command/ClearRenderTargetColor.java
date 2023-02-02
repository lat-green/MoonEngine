package com.greentree.engine.moon.render.pipeline.target.buffer.command;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;

public record ClearRenderTargetColor(RenderLibrary library, Color color) implements TargetCommand {
	
	@Override
	public void run() {
		library.clearRenderTargetColor(color);
	}
	
}
