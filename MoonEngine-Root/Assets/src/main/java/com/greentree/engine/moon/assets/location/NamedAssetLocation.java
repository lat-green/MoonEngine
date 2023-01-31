package com.greentree.engine.moon.assets.location;

import java.util.stream.Stream;

public interface NamedAssetLocation extends AssetLocation {
	
	Stream<String> names();
	
}
