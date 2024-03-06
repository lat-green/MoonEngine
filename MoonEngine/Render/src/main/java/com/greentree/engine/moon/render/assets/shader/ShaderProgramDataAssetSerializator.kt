package com.greentree.engine.moon.render.assets.shader

import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.base.assets.text.PropertyAssetKey
import com.greentree.engine.moon.render.shader.ShaderData
import com.greentree.engine.moon.render.shader.ShaderLanguage
import com.greentree.engine.moon.render.shader.ShaderProgramDataImpl
import com.greentree.engine.moon.render.shader.ShaderType

data object ShaderProgramDataAssetSerializator : AssetSerializator<ShaderProgramDataImpl> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): ShaderProgramDataImpl {
		val vertProp: ResourceAssetKey = ResourceAssetKey(PropertyAssetKey(key, "vert"))
		val fragProp: ResourceAssetKey = ResourceAssetKey(PropertyAssetKey(key, "frag"))
//		val geomProp: ResourceAssetKey = ResourceAssetKey(PropertyAssetKey(key, "geom"))
		val vertKey: ShaderAssetKey = ShaderAssetKey(
			vertProp, ShaderType.VERTEX,
			ShaderLanguage.GLSL
		)
		val fragKey: ShaderAssetKey = ShaderAssetKey(
			fragProp, ShaderType.FRAGMENT,
			ShaderLanguage.GLSL
		)
//		val geomKey: ShaderAssetKey = ShaderAssetKey(
//			geomProp, ShaderType.GEOMETRY,
//			ShaderLanguage.GLSL
//		)
		val vert = manager.load(ShaderData::class.java, vertKey)
		val frag = manager.load(ShaderData::class.java, fragKey)
//		val geom = manager.load(ShaderData::class.java, geomKey)
		return ShaderProgramDataImpl(vert, frag)
	}
}
