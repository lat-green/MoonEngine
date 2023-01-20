package com.greentree.engine.moon.base.assets.scene;

import static java.util.Arrays.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.greentree.commons.assets.key.ResultAssetKeyImpl;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.commons.util.classes.ObjectBuilder;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.commons.util.exception.MultiException;
import com.greentree.commons.xml.XMLElement;

public class ObjectXMLBuilder {
	
	@SuppressWarnings({"unchecked"})
	private static <T> TypeInfo<T> getNotPrimitive(TypeInfo<T> type) {
		if(type.toClass().isPrimitive())
			return (TypeInfo<T>) TypeInfoBuilder
					.getTypeInfo(ClassUtil.getNotPrimitive(type.toClass()));
		return type;
	}
	
	private static <T> T newInstanse(LoadContext context, Class<T> type,
			Map<String, XMLElement> names) {
		var c = ObjectBuilder.getMaxConstructor(type, names.keySet());
		if(c == null)
			c = ObjectBuilder.getMinConstructor(type);
		if(c == null)
			throw new UnsupportedOperationException(type + " not have constructor(maybe abstract)");
		var args = new Object[c.getParameterCount()];
		try {
			var params = c.getParameters();
			for(var i = 0; i < params.length; i++) {
				var p = params[i];
				final var p_type = getNotPrimitive(
						TypeInfoBuilder.getTypeInfo(p.getParameterizedType()));
				args[i] = newInstanseFromXMLValue(context, p_type, names.get(p.getName()));
				names.remove(p.getName());
			}
			return newInstanse(context, type, names, c.newInstance(args));
		}catch(InvocationTargetException e) {
			throw new RuntimeException(
					"args: " + asList(args) + " constructor: " + asList(c.getParameters()).stream()
							.map(Parameter::getName).collect(Collectors.toList()) + " type:" + type,
					e.getCause());
		}catch(Exception e) {
			throw new RuntimeException(
					"args: " + asList(args) + " constructor: " + asList(c.getParameters()).stream()
							.map(Parameter::getName).collect(Collectors.toList()) + " type:" + type,
					e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T newInstanse(LoadContext context, Class<T> type,
			Map<String, XMLElement> names, Object obj) throws Exception {
		for(var e : names.entrySet()) {
			final var f = ClassUtil.getFields(type, e.getKey());
			if(f == null)
				throw new IllegalArgumentException("not found field " + e.getKey());
			if(Modifier.isFinal(f.getModifiers())) {
				final var value = ClassUtil.getField(obj, f);
				final var new_value = newInstanseFromXMLValue(context, f.getType(), e.getValue(),
						value);
				if(value != new_value)
					throw new IllegalArgumentException("change const " + f);
				continue;
			}
			var value = ClassUtil.getField(obj, f);
			if(value != null)
				value = newInstanseFromXMLValue(context, f.getType(), e.getValue(), value);
			else
				value = newInstanseFromXMLValue(context, f, e.getValue());
			ClassUtil.setField(obj, f, value);
		}
		return (T) obj;
	}
	
	private static <T> T newInstanseFromXML(LoadContext context, Class<T> type,
			XMLElement xml_element) {
		final var names = new HashMap<String, XMLElement>();
		
		for(var xml_field : xml_element.getChildrens("field")) {
			final var name = xml_field.getAttribute("name");
			names.put(name, xml_field.getChildren("value"));
		}
		for(var xml_property : xml_element.getChildrens("property")) {
			final var name = xml_property.getAttribute("name");
			final var value = xml_property.getContent();
			final var xml_value = new XMLElement(List.of(), Map.of(), "value", value);
			names.put(name, xml_value);
		}
		
		return newInstanse(context, type, names);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T newFromXML(LoadContext context, XMLElement xml_element)
			throws ClassNotFoundException {
		final var systemClassName = xml_element.getAttribute("type");
		final Class<T> systemClass;
		try {
			systemClass = (Class<T>) ClassUtil.loadClassInAllPackages(systemClassName);
		}catch(ClassNotFoundException e) {
			throw e;
		}
		return newInstanseFromXML(context, systemClass, xml_element);
	}
	
	private static <T> T newInstanseFromXML(LoadContext context, Class<T> type,
			XMLElement xml_element, Object dest) throws Exception {
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
		
		return newInstanse(context, type, names, dest);
	}
	
	private static <T> T newInstanseFromXMLValue(LoadContext context, Class<T> type,
			XMLElement xml_value, Object dest) {
		return newInstanseFromXMLValue(context, TypeInfoBuilder.getTypeInfo(type), xml_value, dest);
	}
	
	private static <T> T newInstanseFromXMLValue(LoadContext context, Field field,
			XMLElement xml_value) {
		return newInstanseFromXMLValue(context, TypeInfoBuilder.getTypeInfo(field), xml_value);
	}
	
	@SuppressWarnings({"unchecked"})
	private static <T> T newInstanseFromXMLValue(LoadContext context, TypeInfo<T> type,
			XMLElement xml_value) {
		{
			final var xml_type = xml_value.getAttribute("type");
			if(xml_type != null) {
				final Class<T> ftype;
				try {
					ftype = (Class<T>) ClassUtil.loadClassInAllPackages(xml_type);
				}catch(ClassNotFoundException e) {
					throw new RuntimeException(e);
				}
				type = TypeInfoBuilder.getTypeInfo(ftype);
			}
		}
		try {
			if(ClassUtil.isExtends(Value.class, type.toClass())) {
				final var xml_value_text = xml_value.getContent();
				final var value_type = getNotPrimitive(type.getTypeArguments()[0]);
				final var value = context.load(value_type, xml_value_text);
				return (T) value;
			}
			final var xml_value_text = xml_value.getContent();
			final var value = context.load(type, new ResultAssetKeyImpl(xml_value_text));
			return value.get();
		}catch(Exception e) {
			try {
				return newInstanseFromXML(context, type.toClass(), xml_value);
			}catch(Exception e1) {
				if(ClassUtil.isExtends(type.toClass(), ArrayList.class)) {
					final var element_type = type.getTypeArguments()[0];
					final var arr = new ArrayList<>();
					for(var xml_element : xml_value.getChildrens()) {
						final var element = newInstanseFromXMLValue(context, element_type,
								xml_element);
						arr.add(element);
					}
					return (T) arr;
				}
				throw new MultiException("type:" + type + " xml_value:" + xml_value, e, e1);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T newInstanseFromXMLValue(LoadContext context, TypeInfo<T> type,
			XMLElement xml_value, Object dest) {
		try {
			if(ClassUtil.isExtends(Value.class, type.toClass())) {
				final var xml_value_text = xml_value.getContent();
				final var value_type = getNotPrimitive(type.getTypeArguments()[0]);
				final var value = context.load(value_type, xml_value_text);
				return (T) value;
			}
			type = getNotPrimitive(type);
			final var xml_value_text = xml_value.getContent();
			final var value = context.load(type, new ResultAssetKeyImpl(xml_value_text));
			return value.get();
		}catch(Exception e) {
			try {
				return newInstanseFromXML(context, type.toClass(), xml_value, dest);
			}catch(Exception e1) {
				throw new MultiException("type:" + type + " xml_value:" + xml_value, e, e1);
			}
		}
	}
	
}
