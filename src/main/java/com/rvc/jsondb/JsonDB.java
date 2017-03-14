package com.rvc.jsondb;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nurmuhammad
 */
public class JsonDB {

    private static final String SP = File.separator;
    private static final String EXT = ".json";

    private Path path;

    public JsonDB(String path) {
        this.path = Paths.get(path);
        try {
            if (Files.notExists(this.path, LinkOption.NOFOLLOW_LINKS)) {
                Files.createDirectories(this.path);
            }
        } catch (IOException e) {
            throw new JsonDBException("I/O exception.", e);
        }

    }

    private String zero(long number, int count) {
        StringBuilder result = new StringBuilder(String.valueOf(number));
        count = count - result.length();
        if (count < 0) throw new JsonDBException("Count must greater then length of number.");
        for (int i = 0; i < count; i++) {
            result.insert(0, "0");
        }
        return result.toString();
    }

    String uniqId(String collection) {
        long nano = System.nanoTime();
        long n1 = nano - (nano / 10_000L) * 10_000L;
        long milli = System.currentTimeMillis();
        long m1 = milli / 1000;
        long m2 = milli - m1 * 1000;
        String id = String.valueOf(m1) + "-" + zero(m2, 3) + "-" + zero(n1, 4);
        if (contains(collection, id)) {
            return uniqId(collection);
        }
        return id;
    }

    public boolean contains(String collection, String id) {
        Path file = Paths.get(path.toString(), collection, id + EXT);
        return Files.exists(file, LinkOption.NOFOLLOW_LINKS);
    }

    public BaseObject save(BaseObject baseObject, String collection) {
        if (baseObject.getId() != null) {
            return save(baseObject, collection, baseObject.getId());
        }
        return save(baseObject, collection, uniqId(collection));
    }

    public BaseObject save(BaseObject baseObject, String collection, String id) {
        write(baseObject.toJSONString(), collection, id);
        baseObject.setId(id);
        return baseObject;
    }

    public BaseObject save(JsonObject jsonObject, String collection, String id) {
        BaseObject baseObject = new BaseObject(id, collection, jsonObject);
        write(baseObject.toJSONString(), collection, id);
        return baseObject;
    }

    public BaseObject save(JsonObject jsonObject, String collection) {
        return save(jsonObject, collection, uniqId(collection));
    }

    public BaseObject save(String json, String collection) {
        return save(json, collection, uniqId(collection));
    }

    public BaseObject save(String json, String collection, String id) {
        BaseObject baseObject = new BaseObject(id, collection, json);
        write(json, collection, id);
        return baseObject;
    }

    private void write(String content, String collection, String id) {
        try {
            Path path = Paths.get(this.path.toString(), collection, id + EXT);

            if (Files.notExists(path.getParent(), LinkOption.NOFOLLOW_LINKS)) {
                Files.createDirectories(path.getParent());
            }

            if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
                Files.createFile(path);
            }
            Files.write(path, content.getBytes());
        } catch (IOException ex) {
            throw new JsonDBException("Write error. id=" + id, ex);
        }
    }

    public BaseObject get(String collection, String id) {
        if (!contains(collection, id)) {
            return null;
        }

        try {
            Path path = Paths.get(this.path.toString(), collection, id + EXT);
            String content = new String(Files.readAllBytes(path));
            return new BaseObject(id, collection, content);
        } catch (IOException e) {
            throw new JsonDBException("I/O exception.", e);
        }
    }

    public List<BaseObject> list(String collection) {
        List<BaseObject> list = new ArrayList<>();
        ids(collection).forEach(id -> {
            list.add(get(collection, id));
        });
        return list;
    }

    public List<String> ids(String collection) {
        List<String> list = new ArrayList<>();
        Path directoryPath = Paths.get(this.path.toString(), collection);

        if (Files.isDirectory(directoryPath)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath, "*" + EXT)) {
                for (Path path : stream) {
                    if (Files.isDirectory(path)) {
                        continue;
                    }
                    String id = path.getFileName().toString().replace(EXT, "");
                    list.add(id);
                }
            } catch (IOException e) {
                throw new JsonDBException("I/O exception", e);
            }
        }
        return list;
    }

    public void delete(String collection, String id) {
        try {
            if (contains(collection, id)) {
                Path path = Paths.get(this.path.toString(), collection, id + EXT);
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new JsonDBException("I/O exception", e);
        }
    }

}
