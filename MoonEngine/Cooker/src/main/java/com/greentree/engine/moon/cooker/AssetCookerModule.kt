package com.greentree.engine.moon.cooker

import com.greentree.engine.moon.modules.LaunchModule
import com.greentree.engine.moon.modules.TerminateModule

interface AssetCookerModule : LaunchModule, TerminateModule {

	override fun terminate() {}
}