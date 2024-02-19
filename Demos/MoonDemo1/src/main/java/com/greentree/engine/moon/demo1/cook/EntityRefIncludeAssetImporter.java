package com.greentree.engine.moon.demo1.cook;

import com.greentree.commons.util.string.RefStringBuilder;
import com.greentree.commons.xml.SAXXMLParser;
import com.greentree.commons.xml.XMLElement;
import com.greentree.engine.moon.cooker.AssetImporter;
import com.greentree.engine.moon.cooker.info.AssetInfo;
import com.greentree.engine.moon.cooker.info.ImportAssetInfo;
import com.greentree.engine.moon.cooker.info.TextImportAssetInfo;
import com.greentree.engine.moon.ecs.component.Component;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

public class EntityRefIncludeAssetImporter implements AssetImporter {

    @Override
    public ImportAssetInfo importAsset(Context context, AssetInfo asset) {
        if (asset.getFileType().equals("scene") || asset.getFileType().equals("entity")) {
            XMLElement xml;
            try (final var in = asset.open()) {
                xml = SAXXMLParser.parse(in);
            } catch (IOException | ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
            xml = includeEntityRefs(context, xml);
            return new TextImportAssetInfo(xml.toString());
        }
        return null;
    }

    private XMLElement includeEntityRefs(Context context, XMLElement xml) {
        if ("entity_ref".equals(xml.getName())) {
            var file = xml.getAttribute("file");
            var map = new HashMap<String, String>();
            for (var xmlProperty : xml.getChildrens("property")) {
                var name = xmlProperty.getAttribute("name");
                var value = xmlProperty.getContent();
                map.put(name, value);
            }
            final XMLElement entity;
            try (final var in = context.readFile(file)) {
                var text = RefStringBuilder.build(in).toString(map);
                try (final var bin = new ByteArrayInputStream(text.getBytes())) {
                    entity = SAXXMLParser.parse(bin);
                }
            } catch (IOException | ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
            return includeEntityRefs(context, entity);
        }
        var attributes = xml.getAttributes();
        if (attributes.containsKey("type")) {
            attributes = new HashMap<>(attributes);
            try {
                var baseType = switch (xml.getName()) {
                    case "component" -> Component.class;
                    default -> Object.class;
                };
                var fullType = context.findClass(baseType, attributes.get("type")).getName();
                attributes.put("type", fullType);
            } catch (ClassNotFoundException ignored) {
            }
        }
        return new XMLElement(xml.getChildrens().stream().map(x -> includeEntityRefs(context, x)).toList(), attributes, xml.getName(), xml.getContent());
    }

}
