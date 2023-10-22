import com.greentree.engine.moon.modules.EngineModule;
import com.greentree.engine.moon.signals.CreateDevicesPropertyModule;

open module engine.moon.signals {
	
	requires transitive engine.moon.base;
	
	exports com.greentree.engine.moon.signals;
	exports com.greentree.engine.moon.signals.ui;
	exports com.greentree.engine.moon.signals.device;
	exports com.greentree.engine.moon.signals.device.value;
	
	provides EngineModule with CreateDevicesPropertyModule;
	
}
