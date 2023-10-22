import com.greentree.engine.moon.demo1.InitAssetModule;
import com.greentree.engine.moon.demo1.InitSceneModule;
import com.greentree.engine.moon.modules.EngineModule;

open module game.moon.demo1 {
    requires transitive engine.moon.script;
    requires transitive engine.moon.opengl;
    requires transitive engine.moon.assimp;
    requires transitive engine.moon.debug;
    provides EngineModule with InitAssetModule, InitSceneModule;
}
