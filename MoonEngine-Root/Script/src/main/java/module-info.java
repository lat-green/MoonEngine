import com.greentree.engine.moon.module.EngineModule;
import com.greentree.engine.moon.script.ScriptsModule;

open module engine.moon.script {
	
	requires transitive engine.moon.base;
	requires org.mozilla.rhino;
	
	exports com.greentree.engine.moon.script;
	exports com.greentree.engine.moon.script.assets;
	exports com.greentree.engine.moon.script.javascript;
	
	provides EngineModule with ScriptsModule;
	
}
