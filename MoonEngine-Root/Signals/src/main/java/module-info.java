import com.greentree.engine.moon.module.EngineModule;
import com.greentree.engine.moon.signals.CreateDevicesPropertyModule;
import com.greentree.engine.moon.signals.device.UpdateDevicesPropertyModule;

open module engine.moon.signals {
	
	requires transitive engine.moon.render;
	
	exports com.greentree.engine.moon.signals;
	exports com.greentree.engine.moon.signals.ui;
	exports com.greentree.engine.moon.signals.device;
	exports com.greentree.engine.moon.signals.device.value;
	exports com.greentree.engine.moon.signals.keyboard;
	exports com.greentree.engine.moon.signals.mouse;
	exports com.greentree.engine.moon.signals.controller;
	
	provides EngineModule with CreateDevicesPropertyModule, UpdateDevicesPropertyModule;
	
}
