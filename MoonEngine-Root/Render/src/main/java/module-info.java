import com.greentree.engine.moon.modules.EngineModule;
import com.greentree.engine.moon.render.WindowModule;
import com.greentree.engine.moon.render.assets.RenderAssetSerializatorModule;
import com.greentree.engine.moon.render.window.ExitOnWindowShouldClose;

open module engine.moon.render {
    requires transitive org.joml;
    requires transitive engine.moon.ecs;
    requires transitive engine.moon.signals;
    requires transitive engine.moon.assets;
    requires transitive engine.moon.mesh;
    requires transitive commons.graphics.smart;
    requires transitive commons.image;
    requires transitive kotlin.stdlib;
    exports com.greentree.engine.moon.render.assets;
    exports com.greentree.engine.moon.render.assets.shader;
    exports com.greentree.engine.moon.render.assets.material;
    exports com.greentree.engine.moon.render.assets.image;
    exports com.greentree.engine.moon.render.assets.image.cube;
    exports com.greentree.engine.moon.render.assets.texture;
    exports com.greentree.engine.moon.render.assets.texture.cube;
    exports com.greentree.engine.moon.render;
    exports com.greentree.engine.moon.render.mesh;
    exports com.greentree.engine.moon.render.shader;
    exports com.greentree.engine.moon.render.camera;
    exports com.greentree.engine.moon.render.window;
    exports com.greentree.engine.moon.render.window.callback;
    exports com.greentree.engine.moon.render.texture;
    exports com.greentree.engine.moon.render.light;
    exports com.greentree.engine.moon.render.light.point;
    exports com.greentree.engine.moon.render.light.direction;
    exports com.greentree.engine.moon.render.pipeline;
    provides EngineModule with RenderAssetSerializatorModule, WindowModule, ExitOnWindowShouldClose;
}
