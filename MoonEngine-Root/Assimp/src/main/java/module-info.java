import com.greentree.engine.moon.mesh.assimp.assets.AssimpAssetSerializatorModule;
import com.greentree.engine.moon.modules.EngineModule;

open module engine.moon.assimp {
	
	requires transitive engine.moon.base;
	requires transitive engine.moon.mesh;
	
	requires transitive commons.image;
	
	requires org.lwjgl.assimp;
	
	exports com.greentree.engine.moon.mesh.assimp.assets;
	
	provides EngineModule with AssimpAssetSerializatorModule;
	
}
