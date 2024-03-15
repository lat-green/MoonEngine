package test.com.greentree.engine.moon.assets.manager

import com.greentree.commons.tests.aop.AutowiredArgument
import com.greentree.commons.tests.aop.AutowiredProvider
import com.greentree.engine.moon.assets.manager.AssetManager
import com.greentree.engine.moon.assets.manager.MutableAssetManager
import com.greentree.engine.moon.assets.manager.ParallelAssetManager
import com.greentree.engine.moon.assets.manager.SimpleAssetManager
import test.com.greentree.engine.moon.assets.Person
import test.com.greentree.engine.moon.assets.loader.AlwaysNotSupportedTypeAssetLoader
import test.com.greentree.engine.moon.assets.loader.AlwaysNotValidSourceAssetLoader
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AssetManagerTestConfig {

	@AutowiredProvider
	fun simpleAssetManager() = SimpleAssetManager()

	@AutowiredProvider(tags = ["parallel"])
	fun parallelAssetManager(executor: ExecutorService) = ParallelAssetManager(executor)

	@AutowiredProvider
	fun cachedThreadPool() = Executors.newCachedThreadPool()

	@AutowiredProvider
	fun fixedThreadPool() = Executors.newFixedThreadPool(8)

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
