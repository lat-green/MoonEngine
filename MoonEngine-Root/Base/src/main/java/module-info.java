import com.greentree.commons.data.file.DataSerializer;
import com.greentree.engine.moon.base.EntitySerializer;

open module engine.moon.base {
	
	requires transitive engine.moon.kernel;
	requires transitive engine.moon.ecs;
	
	requires transitive commons.assets;
	requires transitive commons.math;
	requires transitive commons.xml;
	
	requires transitive com.google.gson;
	
	exports com.greentree.engine.moon.base;
	exports com.greentree.engine.moon.base.assets;
	exports com.greentree.engine.moon.base.assets.xml;
	exports com.greentree.engine.moon.base.assets.json;
	exports com.greentree.engine.moon.base.assets.text;
	exports com.greentree.engine.moon.base.assets.array;
	exports com.greentree.engine.moon.base.assets.number;
	exports com.greentree.engine.moon.base.assets.properties;
	exports com.greentree.engine.moon.base.assets.scene;
	
	exports com.greentree.engine.moon.base.time;
	exports com.greentree.engine.moon.base.name;
	exports com.greentree.engine.moon.base.layer;
	exports com.greentree.engine.moon.base.scene;
	exports com.greentree.engine.moon.base.transform;
	
	provides DataSerializer with EntitySerializer;
	
	
	
}
