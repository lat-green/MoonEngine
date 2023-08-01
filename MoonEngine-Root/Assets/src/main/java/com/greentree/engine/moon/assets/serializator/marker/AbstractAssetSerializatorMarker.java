package com.greentree.engine.moon.assets.serializator.marker;

public abstract class AbstractAssetSerializatorMarker extends Exception {

    public AbstractAssetSerializatorMarker(String message, Throwable cause) {
        super(message, cause, true, false);
    }

}
