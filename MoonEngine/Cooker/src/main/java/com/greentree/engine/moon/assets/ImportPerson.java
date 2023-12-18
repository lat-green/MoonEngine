package com.greentree.engine.moon.assets;

public class ImportPerson {

    public static void main(String[] args) {
        var person = PersonAssetImporter.INSTANCE.importForm(ConsoleAsserImporterContext.INSTANCE);
        System.out.println(person);
    }

}
