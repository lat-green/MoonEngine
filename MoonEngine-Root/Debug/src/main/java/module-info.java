import com.greentree.engine.moon.debug.ExitOnESCAPE;
import com.greentree.engine.moon.debug.LogFPSSystem;
import com.greentree.engine.moon.ecs.system.ECSSystem;
import com.greentree.engine.moon.modules.EngineModule;

open module engine.moon.debug {
    requires transitive engine.moon.signals;
    exports com.greentree.engine.moon.debug;
    provides ECSSystem with LogFPSSystem;
    provides EngineModule with ExitOnESCAPE;
}
