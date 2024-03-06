package com.greentree.engine.moon.opengl.assets.texture

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat
import com.greentree.common.graphics.sgl.texture.gl.cubemap.GLCubeMapTexture
import com.greentree.commons.image.image.ImageData
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.opengl.GLEnums
import com.greentree.engine.moon.render.texture.CubeTextureData
import com.greentree.engine.moon.render.texture.Texture3DType

data object GLCubeTextureAssetSerializator : AssetSerializator<GLCubeMapTexture> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): GLCubeMapTexture {
		val texture = manager.load<CubeTextureData>(key)
		val tex: GLCubeMapTexture = GLCubeMapTexture()
		tex.bind()
		val iter: Iterator<ImageData> = texture.image.iterator()
		for(d in tex.dirs()) {
			val image: ImageData = iter.next()
			d.setData(
				GLPixelFormat.RGB, image.width, image.height,
				GLPixelFormat.gl(image.format), image.data
			)
		}
		val type: Texture3DType = texture.type
		tex.setMagFilter(GLEnums.get(type.filteringMag))
		tex.setMagFilter(GLEnums.get(type.filteringMin))
		tex.setWrapX(GLEnums.get(type.wrapX))
		tex.setWrapY(GLEnums.get(type.wrapY))
		tex.setWrapZ(GLEnums.get(type.wrapZ))
		tex.generateMipmap()
		tex.unbind()
		return tex
	}
}
