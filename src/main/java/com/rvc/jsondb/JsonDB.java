package com.rvc.jsondb;

import org.json.simple.JSONObject;

import java.io.File;
import java.util.List;

/**
 * @author Nurmuhammad
 *
 */
public class JsonDB {

    private static final String SP = File.separator;

    private String path;

    public JsonDB(String path) {
        this.path = path;

    }

    public BaseObject save(BaseObject baseObject, String collection){
        return null;
    }

    public BaseObject save(BaseObject baseObject, String collection, String id){
        return null;
    }

    public BaseObject save(JSONObject jsonObject, String collection, String id){
        return null;
    }

    public BaseObject save(JSONObject jsonObject, String collection){
        return null;
    }

    public BaseObject save(String json, String collection, String id){
        return null;
    }

    public BaseObject save(String json, String collection){
        return null;
    }

    public BaseObject get(String collection, String id){
        return null;
    }

    public List<BaseObject> list(String collection){
        return null;
    }

    public List<String> ids(String collection){
        return null;
    }

    public void delete(String collection, String id){

    }

}
