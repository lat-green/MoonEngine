package com.greentree.engine.moon.render.assets.material

import com.greentree.commons.graphics.smart.shader.Shader
import com.greentree.commons.graphics.smart.shader.material.AbstractMaterial
import com.greentree.commons.graphics.smart.shader.material.Material

interface DeferredMaterial : AbstractMaterial {

	fun copy(): DeferredMaterial

	fun build(shader: Shader): Material
}
