package test.com.greentree.engine.moon.assets

import com.greentree.commons.tests.aop.AutowiredArgument
import com.greentree.commons.tests.aop.AutowiredProvider
import com.greentree.engine.moon.assets.manager.AssetManager
import com.greentree.engine.moon.assets.manager.MutableAssetManager
import com.greentree.engine.moon.assets.manager.SimpleAssetManager
import test.com.greentree.engine.moon.assets.loader.AlwaysNotSupportedTypeAssetLoader
import test.com.greentree.engine.moon.assets.loader.AlwaysNotValidSourceAssetLoader

class TestConfig {

	@AutowiredProvider
	fun simpleAssetManager() = SimpleAssetManager()

	@AutowiredProvider
	fun withNotValidSource(manager: MutableAssetManager): AssetManager {
		manager.addLoader(AlwaysNotValidSourceAssetLoader)
		return manager
	}

	@AutowiredProvider
	fun withNotSupportedType(manager: MutableAssetManager): AssetManager {
		manager.addLoader(AlwaysNotSupportedTypeAssetLoader)
		return manager
	}

	@AutowiredProvider(tags = ["data"])
	fun somePerson(name: String) = Person(name, 46)

	@AutowiredProvider(tags = ["data", "integer"])
	fun textStringInteger(i: Integer) = i.toString()

	@AutowiredProvider(tags = ["data"])
	fun textString() = "text text"

	@AutowiredProvider(tags = ["data"])
	fun emptyString() = ""

	@AutowiredProvider(tags = ["data"])
	fun someInteger() = 42 as Integer

	@AutowiredProvider(tags = ["data"])
	fun pairData(
		@AutowiredArgument(tags = ["data"]) first: Any,
		@AutowiredArgument(tags = ["data"]) second: Any,
	) = first to second
}
