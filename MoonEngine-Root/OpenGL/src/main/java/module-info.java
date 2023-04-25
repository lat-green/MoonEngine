import com.greentree.engine.moon.ecs.system.ECSSystem;
import com.greentree.engine.moon.modules.EngineModule;
import com.greentree.engine.moon.opengl.CreateRenderLibraryModule;
import com.greentree.engine.moon.opengl.CreateWindowLibraryModule;
import com.greentree.engine.moon.opengl.OpenGLInitModule;
import com.greentree.engine.moon.opengl.OpenGLPollDelete;
import com.greentree.engine.moon.opengl.WindowUpdateEvents;
import com.greentree.engine.moon.opengl.assets.OpenGLInitAssetManagerModule;

open module engine.moon.opengl {
	
	requires common.graphics.sgl;
	requires transitive engine.moon.render;
	
	provides EngineModule with CreateRenderLibraryModule, CreateWindowLibraryModule,
	OpenGLInitAssetManagerModule, OpenGLInitModule, OpenGLPollDelete;
	
	provides ECSSystem with WindowUpdateEvents;
}
