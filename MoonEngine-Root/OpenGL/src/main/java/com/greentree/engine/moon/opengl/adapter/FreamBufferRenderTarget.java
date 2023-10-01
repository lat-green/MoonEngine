package com.greentree.engine.moon.opengl.adapter;

import com.greentree.common.graphics.sgl.freambuffer.FreamBuffer;
import com.greentree.commons.graphics.smart.texture.Texture;
import com.greentree.engine.moon.render.pipeline.target.RenderTarget;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextute;
import com.greentree.engine.moon.render.pipeline.target.buffer.TargetCommandBuffer;

import java.util.Objects;

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
    public Texture getColorTexture(int index) {
        final var texture = framebuffer.getColorTexture(index);
        return new OpenGLTexture(texture);
    }

    @Override
    public Texture getDepthTexture() {
        final var texture = framebuffer.getDepthTexture();
        return new OpenGLTexture(texture);
    }

}
