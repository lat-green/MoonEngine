import com.greentree.engine.moon.collision2d.assets.InitAssetsModule;
import com.greentree.engine.moon.modules.EngineModule;

open module engine.moon.collision2d {
    requires transitive engine.moon.base;
    requires transitive commons.geometry;
    requires kotlin.stdlib;
    exports com.greentree.engine.moon.collision2d;
    provides EngineModule with InitAssetsModule;
}
