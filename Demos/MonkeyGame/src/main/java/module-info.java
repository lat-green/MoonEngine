import com.greentree.engine.moon.ecs.system.ECSSystem;
import com.greentree.engine.moon.modules.EngineModule;
import com.greentree.engine.moon.monkey.InitControllerModule;

open module game.moon.monkey {
    requires transitive engine.moon.opengl;
    requires transitive engine.moon.script;
    requires transitive engine.moon.collision2d;
    requires transitive commons.util;
    //	requires transitive common.cooker;
    provides EngineModule with InitControllerModule;
}
