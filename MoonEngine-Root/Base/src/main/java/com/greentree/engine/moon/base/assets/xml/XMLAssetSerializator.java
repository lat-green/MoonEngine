package com.greentree.engine.moon.base.assets.xml;

import java.io.ByteArrayInputStream;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.xml.SAXXMLParser;
import com.greentree.commons.xml.XMLElement;

public class XMLAssetSerializator implements AssetSerializator<XMLElement> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(Resource.class, key) || manager.canLoad(String.class, key);
	}
	
	@Override
	public Value<XMLElement> load(LoadContext context, AssetKey ckey) {
		{
			final var res = context.load(Resource.class, ckey);
			if(res != null)
				return context.map(res, XMLFunction.INSTANCE);
		}
		{
			final var text = context.load(String.class, ckey);
			if(text != null)
				return context.map(text, XMLTextFunction.INSTANCE);
		}
		return null;
	}
	
	private static final class XMLFunction implements Value1Function<Resource, XMLElement> {
		
		private static final long serialVersionUID = 1L;
		
		public static final XMLFunction INSTANCE = new XMLFunction();
		
		@Override
		public XMLElement apply(Resource res) {
			try(final var in = res.open()) {
				return SAXXMLParser.parse(in);
			}catch(Exception e) {
				throw new IllegalArgumentException(res.toString(), e);
			}
		}
		
	}
	
	private static final class XMLTextFunction implements Value1Function<String, XMLElement> {
		
		private static final long serialVersionUID = 1L;
		
		public static final XMLTextFunction INSTANCE = new XMLTextFunction();
		
		@Override
		public XMLElement apply(String text) {
			try(final var bout = new ByteArrayInputStream(text.getBytes());) {
				return SAXXMLParser.parse(bout);
			}catch(Exception e) {
				throw new IllegalArgumentException(text, e);
			}
		}
		
	}
	
}
