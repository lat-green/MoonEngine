import com.greentree.engine.moon.module.EngineModule;
import com.greentree.engine.moon.opengl.CreateRenderLibraryModule;
import com.greentree.engine.moon.opengl.CreateWindowLibraryModule;
import com.greentree.engine.moon.opengl.OpenGLInitModule;
import com.greentree.engine.moon.opengl.OpenGLPollDelete;
import com.greentree.engine.moon.opengl.assets.OpenGLInitAssetManagerModule;

open module engine.moon.opengl {
	
	requires common.graphics.sgl;
	requires transitive engine.moon.render;
	
	provides EngineModule with CreateRenderLibraryModule, CreateWindowLibraryModule,
			OpenGLInitAssetManagerModule, OpenGLInitModule, OpenGLPollDelete;
	
}
