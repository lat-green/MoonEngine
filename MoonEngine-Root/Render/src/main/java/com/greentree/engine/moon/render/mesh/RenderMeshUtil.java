package com.greentree.engine.moon.render.mesh;

import com.greentree.commons.util.function.SaveFunction;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;

public final class RenderMeshUtil {
	
	private static final Instances INSTANCES = new Instances();
	public final RenderMesh BOX, QUAD;
	
	private RenderMeshUtil(RenderLibrary library) {
		BOX = library.build(MeshUtil.BOX);
		QUAD = library.build(MeshUtil.QUAD);
	}
	
	public static RenderMesh BOX(RenderLibrary library) {
		return get(library).BOX;
	}
	
	public static RenderMeshUtil get(RenderLibrary library) {
		return INSTANCES.apply(library);
	}
	
	public static RenderMesh QUAD(RenderLibrary library) {
		return get(library).QUAD;
	}
	
	
	
	private static final class Instances extends SaveFunction<RenderLibrary, RenderMeshUtil> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		protected RenderMeshUtil create(RenderLibrary library) {
			return new RenderMeshUtil(library);
		}
		
	}
}
