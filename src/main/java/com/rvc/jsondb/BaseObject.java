package com.rvc.jsondb;

import com.google.gson.*;

/**
 * @author Nurmuhammad
 */

public class BaseObject {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private String id;
    private String collection;
    private JsonObject jsonObject;

    public BaseObject(String id, String collection, JsonObject jsonObject) {
        this.id = id;
        this.collection = collection;
        this.jsonObject = jsonObject;
    }

    public BaseObject(String id, String collection, String json) {
        this.id = id;
        this.collection = collection;
        JsonElement jsonElement = GSON.fromJson(json, JsonElement.class);
        jsonObject = jsonElement.getAsJsonObject();
    }

    public BaseObject(String collection) {
        this.collection = collection;
    }

    public BaseObject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public JsonObject getJsonObject() {
        if (jsonObject == null) {
            jsonObject = new JsonObject();
        }
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public BaseObject put(String name, Object value) {
        if(value==null){
            getJsonObject().add(name, JsonNull.INSTANCE);
        } else if (value instanceof String){
            getJsonObject().addProperty(name, (String) value);
        } else if (value instanceof Number){
            getJsonObject().addProperty(name, (Number) value);
        } else if (value instanceof Boolean){
            getJsonObject().addProperty(name, (Boolean) value);
        } else if (value instanceof Character){
            getJsonObject().addProperty(name, (Character) value);
        } else {
            getJsonObject().add(name, GSON.toJsonTree(value));
        }

        return this;
    }

    public JsonElement get(String name) {
        return getJsonObject().get(name);
    }

    public void remove(String name){
        getJsonObject().remove(name);
    }

    public BaseObject save(JsonDB db) {
        if (db == null) {
            throw new JsonDBException("Error in saving baseObject, db is null.");
        }
        if (getCollection() == null) {
            throw new JsonDBException("Error in saving baseObject, collection can not be null.");
        }
        if (getId() == null) {
            BaseObject baseObject = db.save(this, getCollection());
            setId(baseObject.getId());
        } else {
            db.save(this, getCollection(), getId());
        }

        return this;
    }

    public BaseObject delete(JsonDB db) {
        if (db == null) {
            throw new JsonDBException("Error in saving baseObject, db is null.");
        }

        if (getCollection() == null) {
            throw new JsonDBException("Error in saving baseObject, collection can not be null.");
        }

        if (getId() == null) {
            throw new JsonDBException("Error in saving baseObject, id can not be null.");
        }

        db.delete(getCollection(), getId());
        setId(null);
        return this;
    }

    public String toJSONString(){
        return GSON.toJson(jsonObject);
    }

}
