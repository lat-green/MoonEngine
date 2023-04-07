package com.greentree.engine.moon.opengl.adapter;

import java.util.Objects;

import com.greentree.common.graphics.sgl.freambuffer.FreamBuffer;
import com.greentree.engine.moon.render.pipeline.material.Property;
import com.greentree.engine.moon.render.pipeline.target.RenderTarget;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextute;
import com.greentree.engine.moon.render.pipeline.target.buffer.TargetCommandBuffer;


public record FreamBufferRenderTarget(FreamBuffer framebuffer, RenderTarget context)
		implements RenderTargetTextute {
	
	
	public FreamBufferRenderTarget {
		Objects.requireNonNull(framebuffer);
		Objects.requireNonNull(context);
	}
	
	@Override
	public TargetCommandBuffer buffer() {
		return new FreamBufferCommandBuffer(framebuffer, context.buffer());
	}
	
	
	@Override
	public void close() {
		framebuffer.close();
	}
	
	@Override
	public Property getColorTexture(int index) {
		final var texture = framebuffer.getColorTexture(index);
		return new TextureAddapter(texture);
	}
	
	
	@Override
	public Property getDepthTexture() {
		final var texture = framebuffer.getDepthTexture();
		return new TextureAddapter(texture);
	}
	
}
