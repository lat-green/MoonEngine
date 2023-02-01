open module engine.moon.render {
	
	requires transitive engine.moon.base;
	requires transitive engine.moon.mesh;
	
	requires transitive commons.image;
	
	exports com.greentree.engine.moon.render.assets;
	exports com.greentree.engine.moon.render.assets.color;
	exports com.greentree.engine.moon.render.assets.buffer;
	exports com.greentree.engine.moon.render.assets.shader;
	exports com.greentree.engine.moon.render.assets.material;
	exports com.greentree.engine.moon.render.assets.image;
	exports com.greentree.engine.moon.render.assets.image.cube;
	exports com.greentree.engine.moon.render.assets.texture;
	exports com.greentree.engine.moon.render.assets.texture.cube;
	
	exports com.greentree.engine.moon.render;
	exports com.greentree.engine.moon.render.ui;
	exports com.greentree.engine.moon.render.mesh;
	exports com.greentree.engine.moon.render.buffer;
	exports com.greentree.engine.moon.render.camera;
	exports com.greentree.engine.moon.render.material;
	exports com.greentree.engine.moon.render.texture;
	
	exports com.greentree.engine.moon.render.light;
	exports com.greentree.engine.moon.render.light.point;
	exports com.greentree.engine.moon.render.light.direction;
	
	exports com.greentree.engine.moon.render.shader;
	exports com.greentree.engine.moon.render.shader.data;
	
	exports com.greentree.engine.moon.render.pipeline;
	exports com.greentree.engine.moon.render.pipeline.target;
	exports com.greentree.engine.moon.render.pipeline.target.buffer;
	exports com.greentree.engine.moon.render.pipeline.target.buffer.command;
	exports com.greentree.engine.moon.render.pipeline.source;
	exports com.greentree.engine.moon.render.pipeline.source.buffer;
	
}
