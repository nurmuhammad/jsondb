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
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
