package test.com.greentree.engine.moon.assets.loader

import com.greentree.engine.moon.assets.serializator.SimpleAssetSerializator

data object StringToInt : SimpleAssetSerializator<String, Int> {

	override fun invoke(source: String) = source.toInt()
}