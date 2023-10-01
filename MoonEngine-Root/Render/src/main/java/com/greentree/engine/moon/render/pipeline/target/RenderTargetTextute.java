package com.greentree.engine.moon.render.pipeline.target;

import com.greentree.commons.graphics.smart.texture.Texture;

public interface RenderTargetTextute extends RenderTarget {

    default Texture getColorTexture() {
        return getColorTexture(0);
    }

    Texture getColorTexture(int index);

    Texture getDepthTexture();

}
