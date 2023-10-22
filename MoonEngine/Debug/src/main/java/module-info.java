import com.greentree.engine.moon.debug.ExitOnESCAPE;
import com.greentree.engine.moon.debug.LogFPSModule;
import com.greentree.engine.moon.modules.EngineModule;

open module engine.moon.debug {
    requires transitive engine.moon.signals;
    exports com.greentree.engine.moon.debug;
    provides EngineModule with ExitOnESCAPE, LogFPSModule;
}
