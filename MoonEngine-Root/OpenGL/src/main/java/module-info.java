open module engine.moon.opengl {
	
	requires transitive common.graphics.sgl;
	requires transitive engine.moon.signals;
	requires transitive engine.moon.render;
	
	exports com.greentree.engine.moon.opengl;
	
	exports com.greentree.engine.moon.opengl.assets.buffer;
	exports com.greentree.engine.moon.opengl.assets.shader;
	exports com.greentree.engine.moon.opengl.assets.texture;
	exports com.greentree.engine.moon.opengl.assets.material;
	
	exports com.greentree.engine.moon.opengl.render.material;
	exports com.greentree.engine.moon.opengl.render.skybox;
	exports com.greentree.engine.moon.opengl.render.camera;
	
}
