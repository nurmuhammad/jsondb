package com.rvc.jsondb;

import org.json.simple.JSONObject;

/**
 * @author Nurmuhammad
 */

public class BaseObject {

    private String id;
    private String collection;
    private JSONObject jsonObject;

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

    public JSONObject getJsonObject() {
        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @SuppressWarnings("unchecked")
    public BaseObject put(String name, Object value) {
        getJsonObject().put(name, value);
        return this;
    }

    public Object get(String name) {
        if ("id".equalsIgnoreCase(name)) return getId();
        if ("collection".equalsIgnoreCase(name)) return getCollection();
        return getJsonObject().get(name);
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

}
