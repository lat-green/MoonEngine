package com.greentree.engine.moon.render.pipeline.target;

import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.render.pipeline.target.buffer.CameraCommandBuffer;
import com.greentree.engine.moon.render.texture.Texture;

public record CameraRenderTarget(Entity camera, RenderTargetTextute target) implements RenderTargetTextute {

    @Override
    public String toString() {
        return "CameraRenderTarget [" + target + "]";
    }

    @Override
    public CameraCommandBuffer buffer() {
        return new CameraCommandBuffer(camera, target.buffer());
    }

    @Override
    public Texture getColorTexture(int index) {
        return target.getColorTexture(index);
    }

    @Override
    public Texture getDepthTexture() {
        return target.getDepthTexture();
    }

}
