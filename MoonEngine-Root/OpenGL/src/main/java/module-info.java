import com.greentree.engine.moon.module.EngineModule;
import com.greentree.engine.moon.opengl.CreateRenderLibraryModule;
import com.greentree.engine.moon.opengl.CreateWindowLibraryModule;
import com.greentree.engine.moon.opengl.OpenGLInitModule;
import com.greentree.engine.moon.opengl.assets.OpenGLInitAssetManagerModule;

open module engine.moon.opengl {
	
	requires transitive common.graphics.sgl;
	requires transitive engine.moon.render;
	
	exports com.greentree.engine.moon.opengl;
	exports com.greentree.engine.moon.opengl.adapter;
	
	exports com.greentree.engine.moon.opengl.assets;
	exports com.greentree.engine.moon.opengl.assets.shader;
	exports com.greentree.engine.moon.opengl.assets.texture;
	exports com.greentree.engine.moon.opengl.assets.material;
	
	provides EngineModule with CreateRenderLibraryModule, CreateWindowLibraryModule,
			OpenGLInitAssetManagerModule, OpenGLInitModule;
	
}
