package ru.cazmusw.json.utils;

import java.util.HashMap;

public class JsonObject {

    private final HashMap<String, Object> data;

    public JsonObject() {
        this.data = new HashMap<>();
    }

    public void addObject(String key, Object value) {
        this.data.put(key, value);
    }

    public Object getObject(String key) {
        return this.data.get(key);
    }

    public HashMap<String, Object> getData() {
        return this.data;
    }

    public String toString() {
        return this.data.toString();
    }
}
