open module engine.moon.assimp {
	
	requires transitive engine.moon.base;
	requires transitive engine.moon.mesh;
	
	requires commons.image;
	
	requires org.lwjgl.assimp;
	
	exports com.greentree.engine.moon.mesh.assimp.assets;
	
}
