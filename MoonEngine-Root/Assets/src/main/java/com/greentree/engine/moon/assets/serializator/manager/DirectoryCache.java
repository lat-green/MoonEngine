package com.greentree.engine.moon.assets.serializator.manager;

import com.greentree.engine.moon.assets.key.AssetKey;

import java.io.*;
import java.util.function.Supplier;

@Deprecated
public class DirectoryCache<K extends AssetKey, T> implements Cache<K, T> {

    private final File directory;
    private final Cache<K, T> origin;

    public DirectoryCache(File directory) {
        this(directory, new WeakHashMapCache<>());
    }

    public DirectoryCache(File directory, Cache<K, T> origin) {
        this.origin = origin;
        this.directory = directory;
    }

    @Override
    public T get(K key) {
        var info = info(key);
        return info.get();
    }

    private Info info(K key) {
        var file = new File(directory, key.getClass().getSimpleName() + "/" + key.type() + "/" + key.hashCode() + ".asset");
        return new Info(key, file);
    }

    @Override
    public T set(K key, Supplier<T> create) {
        var info = info(key);
        return info.set(create);
    }

    private final class Info {

        private final File file;
        private final K key;

        public Info(K key, File file) {
            this.key = key;
            this.file = file;
        }

        public T set(Supplier<T> create) {
            try {
                if (hasResult())
                    return read();
            } catch (Throwable e) {
            }
            return origin.set(key, () -> {
                var result = create.get();
                write(result);
                return result;
            });
        }

        public boolean hasResult() {
            return file.exists();
        }

        public T read() throws IOException, ClassNotFoundException {
            try (var in = new ObjectInputStream(new FileInputStream(file))) {
                return (T) in.readObject();
            } catch (Throwable e) {
                file.delete();
                throw e;
            }
        }

        public void write(T result) {
            file.getParentFile().mkdirs();
            try (var out = new ObjectOutputStream(new FileOutputStream(file))) {
                out.writeObject(result);
            } catch (Throwable e) {
            }
        }

        public T get() {
            try {
                if (hasResult())
                    return read();
            } catch (Throwable e) {
            }
            return origin.get(key);
        }

    }

}
