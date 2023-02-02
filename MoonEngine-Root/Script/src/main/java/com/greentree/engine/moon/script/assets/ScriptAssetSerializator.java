package com.greentree.engine.moon.script.assets;

import java.io.IOException;

import org.mozilla.javascript.WrappedException;

import com.greentree.commons.data.resource.Resource;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.script.javascript.JavaScriptScript;

public final class ScriptAssetSerializator implements AssetSerializator<JavaScriptScript> {
	
	@Override
	public Value<JavaScriptScript> load(LoadContext context, AssetKey key) {
		{
			if(context.canLoad(Resource.class, key)) {
				final var res = context.load(Resource.class, key);
				return context.map(res, ScriptFunction.INSTANCE);
			}
		}
		return null;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager context, AssetKey key) {
		return context.canLoad(Resource.class, key);
	}
	
	private static final class ScriptFunction
			implements Value1Function<Resource, JavaScriptScript> {
		
		private static final long serialVersionUID = 1L;
		
		public static final ScriptFunction INSTANCE = new ScriptFunction();
		
		@Override
		public JavaScriptScript applyWithDest(Resource resource, JavaScriptScript dest) {
			final String script;
			try(final var in = resource.open()) {
				script = new String(in.readAllBytes());
			}catch(IOException e) {
				throw new WrappedException(e);
			}
			dest.setScript(script);
			return dest;
		}
		
		@Override
		public JavaScriptScript apply(Resource resource) {
			final String script;
			try(final var in = resource.open()) {
				script = new String(in.readAllBytes());
			}catch(IOException e) {
				throw new WrappedException(e);
			}
			return new JavaScriptScript(script);
		}
		
	}
	
}
