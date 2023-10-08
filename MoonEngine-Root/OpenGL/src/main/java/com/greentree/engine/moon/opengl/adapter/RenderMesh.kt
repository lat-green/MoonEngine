package com.greentree.engine.moon.opengl.adapter

import com.greentree.commons.graphics.smart.mesh.Mesh

interface RenderMesh : Mesh {

	fun render(count: Int = 1)
}