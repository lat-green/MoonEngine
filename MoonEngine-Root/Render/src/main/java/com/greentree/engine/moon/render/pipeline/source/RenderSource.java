package com.greentree.engine.moon.render.pipeline.source;

import com.greentree.engine.moon.render.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.source.buffer.SourceCommandBuffer;

public interface RenderSource {
	
	SourceCommandBuffer buffer(MaterialProperties properties);
	
}
