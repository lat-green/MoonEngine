package com.greentree.engine.moon.assets.key

import java.io.Serializable

interface AssetKeyType : Serializable {
	object DEFAULT : AssetKeyType {

		override fun toString(): String {
			return "AssetKeyType.DEFAULT"
		}
	}
}