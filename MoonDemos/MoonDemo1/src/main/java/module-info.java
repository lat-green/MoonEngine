import com.greentree.engine.moon.demo1.InitControllerModule;
import com.greentree.engine.moon.modules.EngineModule;

open module game.moon.demo1 {
    requires transitive engine.moon.opengl;
    requires transitive engine.moon.script;
    requires transitive engine.moon.assimp;
    requires transitive engine.moon.debug;
    provides EngineModule with InitControllerModule;
}
