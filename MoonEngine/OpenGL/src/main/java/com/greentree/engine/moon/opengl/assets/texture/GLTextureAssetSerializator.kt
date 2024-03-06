package com.greentree.engine.moon.opengl.assets.texture

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.opengl.GLEnums
import com.greentree.engine.moon.render.texture.Texture2DData

data object GLTextureAssetSerializator : AssetSerializator<GLTexture2DImpl> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): GLTexture2DImpl {
		val texture = manager.load<Texture2DData>(key)
		val tex = GLTexture2DImpl()
		val img = texture.image
		tex.bind()
		tex.setData(
			GLPixelFormat.RGBA,
			img.width,
			img.height,
			GLPixelFormat.gl(img.format),
			img.data
		)
		val type = texture.type
		tex.setMagFilter(GLEnums.get(type.filteringMag))
		tex.setMagFilter(GLEnums.get(type.filteringMin))
		tex.setWrapX(GLEnums.get(type.wrapX))
		tex.setWrapY(GLEnums.get(type.wrapY))
		tex.generateMipmap()
		tex.unbind()
		return tex
	}
}
