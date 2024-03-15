import com.greentree.engine.moon.modules.EngineModule;
import com.greentree.engine.moon.opengl.*;
import com.greentree.engine.moon.opengl.assets.OpenGLInitAssetManagerModule;

open module engine.moon.opengl {
    requires commons.util;
    requires commons.graphics.sgl;
    requires transitive engine.moon.render;
    requires transitive engine.moon.base;
    requires transitive engine.moon.kernel;
    exports com.greentree.engine.moon.opengl.adapter;
    exports com.greentree.engine.moon.opengl.adapter.buffer;
    exports com.greentree.engine.moon.opengl.adapter.buffer.command;
    provides EngineModule with CreateRenderContextModule, CreateWindowLibraryModule,
            OpenGLInitAssetManagerModule, OpenGLInitModule, OpenGLPollDelete, WindowUpdateEvents;
}
