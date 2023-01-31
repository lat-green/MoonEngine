package com.greentree.engine.moon.base.assets.json;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.greentree.commons.data.resource.Resource;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;

public class JSONAssetSerializator implements AssetSerializator<JsonElement> {
	
	public static final Gson GSON = new GsonBuilder().create();
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof ObjectToJsonKey k)
			return manager.canLoad(Object.class, k.object());
		return manager.canLoad(Resource.class, key) || manager.canLoad(CharSequence.class, key);
	}
	
	@Override
	public Value<JsonElement> load(LoadContext context, AssetKey ckey) {
		if(ckey instanceof ObjectToJsonKey k) {
			final var obj = context.load(Object.class, k.object());
			if(obj != null)
				return context.map(obj, JSONToObjectFunction.INSTANCE);
		}
		{
			final var res = context.load(Resource.class, ckey);
			if(res != null)
				return context.map(res, JSONFunction.INSTANCE);
		}
		{
			final var text = context.load(CharSequence.class, ckey);
			if(text != null)
				return context.map(text, JSONTextFunction.INSTANCE);
		}
		return null;
	}
	
	private static final class JSONFunction implements Value1Function<Resource, JsonElement> {
		
		private static final long serialVersionUID = 1L;
		
		public static final JSONFunction INSTANCE = new JSONFunction();
		
		@Override
		public JsonElement apply(Resource res) {
			try(final var in = res.open();final var reader = new InputStreamReader(in)) {
				return JsonParser.parseReader(reader);
			}catch(IOException e) {
				throw new IllegalArgumentException(res.toString(), e);
			}
		}
		
	}
	
	private static final class JSONTextFunction
			implements Value1Function<CharSequence, JsonElement> {
		
		private static final long serialVersionUID = 1L;
		
		public static final JSONTextFunction INSTANCE = new JSONTextFunction();
		
		@Override
		public JsonElement apply(CharSequence text) {
			try(final var reader = new StringReader(text.toString())) {
				return JsonParser.parseReader(reader);
			}
		}
		
	}
	
	private static final class JSONToObjectFunction implements Value1Function<Object, JsonElement> {
		
		private static final long serialVersionUID = 1L;
		
		public static final JSONToObjectFunction INSTANCE = new JSONToObjectFunction();
		
		@Override
		public JsonElement apply(Object obj) {
			return GSON.toJsonTree(obj);
		}
		
	}
	
}
