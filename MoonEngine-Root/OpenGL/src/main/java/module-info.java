import com.greentree.engine.moon.modules.EngineModule;
import com.greentree.engine.moon.opengl.*;
import com.greentree.engine.moon.opengl.assets.OpenGLInitAssetManagerModule;

open module engine.moon.opengl {
    requires common.graphics.sgl;
    requires transitive engine.moon.render;
    provides EngineModule with CreateRenderLibraryModule, CreateWindowLibraryModule,
            OpenGLInitAssetManagerModule, OpenGLInitModule, OpenGLPollDelete, WindowUpdateEvents, CreateRenderContextModule;
}
