package com.greentree.engine.moon.assets.smart

import com.greentree.engine.moon.assets.asset.Group2
import com.greentree.engine.moon.assets.asset.group
import com.greentree.engine.moon.assets.smart.info.AssetInfo

fun <T1 : Any, T2 : Any> merge(source1: SmartAsset<T1>, source2: SmartAsset<T2>) = Merge2SmartAsset(source1, source2)
fun <T1 : Any, T2 : Any> merge(source1: AssetInfo<T1>, source2: AssetInfo<T2>) = Merge2AssetInfo(source1, source2)
fun merge(source1: SmartAsset.Connection, source2: SmartAsset.Connection) = Merge2Connection(source1, source2)
fun merge(source1: AssetStatus, source2: AssetStatus): AssetStatus {
	if(source1 == AssetStatus.NOT_VALID || source2 == AssetStatus.NOT_VALID)
		return AssetStatus.NOT_VALID
	if(source1 == AssetStatus.LOADING || source2 == AssetStatus.LOADING)
		return AssetStatus.LOADING
	return AssetStatus.VALID
}

class Merge2SmartAsset<T1 : Any, T2 : Any>(private val source1: SmartAsset<T1>, private val source2: SmartAsset<T2>) :
	SmartAsset<Group2<T1, T2>> {

	override fun info(context: SmartAsset.Context): AssetInfo<Group2<T1, T2>> {
		val info1 = source1.info(context)
		val info2 = source2.info(context)
		return merge(info1, info2)
	}

	override fun openConnection() = merge(source1.openConnection(), source2.openConnection())
}

class Merge2Connection(private val source1: SmartAsset.Connection, private val source2: SmartAsset.Connection) :
	SmartAsset.Connection {

	override val isChange
		get() = source1.isChange || source2.isChange
}

class Merge2AssetInfo<T1 : Any, T2 : Any>(private val source1: AssetInfo<T1>, private val source2: AssetInfo<T2>) :
	AssetInfo<Group2<T1, T2>> {

	override val value: Group2<T1, T2>
		get() = group(source1.value, source2.value)
	override val status: AssetStatus
		get() = merge(source1.status, source2.status)
}