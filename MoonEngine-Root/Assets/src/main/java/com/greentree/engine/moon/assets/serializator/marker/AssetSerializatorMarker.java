package com.greentree.engine.moon.assets.serializator.marker;

public abstract class AssetSerializatorMarker extends RuntimeException {

    public AssetSerializatorMarker() {
        this("", null);
    }

    public AssetSerializatorMarker(String message, Throwable cause) {
        super(message, cause, true, false);
    }

}
