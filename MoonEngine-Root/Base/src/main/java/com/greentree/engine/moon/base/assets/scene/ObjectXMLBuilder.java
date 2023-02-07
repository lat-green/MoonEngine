package com.greentree.engine.moon.base.assets.scene;

import static java.util.Arrays.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.commons.util.classes.ObjectBuilder;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.commons.util.classes.info.TypeUtil;
import com.greentree.commons.util.collection.AutoGenerateMap;
import com.greentree.commons.xml.XMLElement;

public class ObjectXMLBuilder implements XMLTypeAddapter.Context {
	
	
	private final Collection<XMLTypeAddapter> addapters = new ArrayList<>() {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public boolean add(XMLTypeAddapter addapter) {
			super.add(0, addapter);
			return true;
		}
		
	};
	
	private final Map<Class<?>, Collection<XMLTypeAddapter>> type_addapters = new AutoGenerateMap<>() {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		protected Collection<XMLTypeAddapter> generate(Class<?> k) {
			return new ArrayList<>();
		}
		
	};
	
	{
		add(new XMLTypeAddapter() {
			
			@Override
			public <T> Constructor<T> newInstance(Context context, TypeInfo<T> type,
					XMLElement xml_element) {
				final var names = getNames(xml_element);
				
				final var cls = type.toClass();
				var c = ObjectBuilder.getMaxConstructor(cls, names.keySet());
				if(c == null)
					c = ObjectBuilder.getMinConstructor(cls);
				if(c == null)
					throw new UnsupportedOperationException(
							type + " not have constructor(maybe abstract) xml_element:"
									+ xml_element + " names:" + names);
				
				var params = c.getParameters();
				var args = new Object[params.length];
				for(var i = 0; i < params.length; i++) {
					var p = params[i];
					final var p_type = TypeInfoBuilder.getTypeInfo(p.getParameterizedType());
					if(p_type == null)
						throw new NullPointerException("null TypeInfo of " + p);
					final var xml_value = names.remove(p.getName());
					if(xml_value == null)
						throw new NullPointerException("constructor of " + type + " has paramtr "
								+ p.getName() + " but xml not " + names);
					try {
						args[i] = context.build(p_type, xml_value);
					}catch(Exception e) {
						throw new RuntimeException("Exception on build constructor parameter " + p
								+ " of type " + type, e);
					}
				}
				try {
					return newInstanceConstructor(names, c.newInstance(args));
				}catch(Exception e) {
					throw new RuntimeException("args: "
							+ asList(args) + " constructor: " + asList(c.getParameters()).stream()
									.map(Parameter::getName).collect(Collectors.toList())
							+ " type:" + type + " names:" + names, e);
				}
			}
		});
		add(new XMLTypeAddapter() {
			
			@SuppressWarnings("rawtypes")
			private static final TypeInfo<ArrayList> ARRAY_LIST_TYPE = TypeInfoBuilder
					.getTypeInfo(ArrayList.class);
			
			@Override
			public Class<?> getLoadOnly() {
				return ArrayList.class;
			}
			
			@Override
			public <T> Constructor<T> newInstance(Context context, TypeInfo<T> type,
					XMLElement element) {
				final var v = newInstanceOrNull(context, type, element);
				if(v != null)
					return new ValueConstructor<>(v);
				return null;
			}
			
			@SuppressWarnings("unchecked")
			public <T> T newInstanceOrNull(Context context, TypeInfo<T> type, XMLElement element) {
				if(TypeUtil.isExtends(type, ARRAY_LIST_TYPE))
					return null;
				final var lement_type = type.getTypeArguments()[0].getBoxing();
				final var result = new ArrayList<>();
				for(var c : element.getChildrens("value")) {
					final var v = context.build(lement_type, c);
					result.add(v);
				}
				return (T) result;
			}
		});
	}
	
	public static final Map<String, XMLElement> getNames(XMLElement xml_element) {
		final var names = new HashMap<String, XMLElement>();
		for(var xml_field : xml_element.getChildrens("field")) {
			final var name = xml_field.getAttribute("name");
			names.put(name, xml_field.getChildrens("value").iterator().next());
		}
		for(var xml_property : xml_element.getChildrens("property")) {
			final var name = xml_property.getAttribute("name");
			final var value = xml_property.getContent();
			final var xml_value = new XMLElement(List.of(), Map.of(), "value", value);
			names.put(name, xml_value);
		}
		return names;
	}
	
	@Deprecated
	public static <T> TypeInfo<T> getNotPrimitive(TypeInfo<T> type) {
		return type.getBoxing();
	}
	
	public void add(XMLTypeAddapter addapter) {
		final var type = addapter.getLoadOnly();
		if(type == null)
			addapters.add(addapter);
		else
			add(type, addapter);
	}
	
	@Override
	public <T> Constructor<T> newInstance(TypeInfo<T> type, XMLElement xml_element) {
		type = type.getBoxing();
		final var typed_addapers = type_addapters.get(type.toClass());
		for(var v : typed_addapers) {
			final var c = v.newInstance(this, type, xml_element);
			if(c != null) {
				return c;
			}
		}
		for(var v : addapters) {
			if(typed_addapers.contains(v))
				continue;
			final var c = v.newInstance(this, type, xml_element);
			if(c != null) {
				add(c.value().getClass(), v);
				return c;
			}
		}
		return null;
	}
	
	private void add(Class<?> type, XMLTypeAddapter addapter) {
		type_addapters.get(type).add(addapter);
		final var stype = type.getSuperclass();
		if(stype != null)
			add(stype, addapter);
		for(var i : type.getInterfaces())
			add(i, addapter);
	}
	
	private <T> Constructor<T> newInstanceConstructor(Map<String, XMLElement> names,
			T newInstance) {
		return new SetFieldsConstructor<>(newInstance, names);
	}
	
	private <T> void setField(T object, Field field, XMLElement value)
			throws IllegalArgumentException, IllegalAccessException {
		final var now_v = ClassUtil.getField(object, field);
		if(now_v != null) {
			if(field.getType().isPrimitive()) {
				final var p_type = TypeInfoBuilder.getTypeInfo(field);
				final var v = build(p_type, value);
				ClassUtil.setField(object, field, v);
			}
			final var names = getNames(value);
			setFields(now_v, names);
			return;
		}
		if(Modifier.isFinal(field.getModifiers()))
			throw new IllegalArgumentException("field:" + field);
		final var p_type = TypeInfoBuilder.getTypeInfo(field);
		final var v = build(p_type, value);
		ClassUtil.setField(object, field, v);
	}
	
	private <T> void setFields(T object, Map<String, XMLElement> names) {
		final var cls = object.getClass();
		final var iter = names.keySet().iterator();
		while(iter.hasNext()) {
			final var name = iter.next();
			try {
				final var field = cls.getDeclaredField(name);
				setField(object, field, names.get(name));
			}catch(NoSuchFieldException | SecurityException | IllegalArgumentException
					| IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			iter.remove();
		}
	}
	
	private final class SetFieldsConstructor<T> implements Constructor<T> {
		
		private final T newInstance;
		private final Map<String, XMLElement> names;
		
		
		
		public SetFieldsConstructor(T newInstance, Map<String, XMLElement> names) {
			this.newInstance = newInstance;
			this.names = names;
		}
		
		@Override
		public void close() {
			setFields(newInstance, names);
		}
		
		@Override
		public String toString() {
			return "SetFieldsConstructor [" + newInstance + ", " + names + "]";
		}
		
		@Override
		public T value() {
			return newInstance;
		}
		
	}
	
}
