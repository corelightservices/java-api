package com.corelightservices.api;

import org.json.JSONObject;

public class CLSPlayer extends CLSObject {

    private String apiReference;
    private String name;

    private CLSPlayer() {}

    static CLSPlayer create(JSONObject object) {
        CLSPlayer instance = new CLSPlayer();
        instance.apiReference = object.getString("apiReference");
        instance.name = object.getString("name");
        return instance;
    }

    public String getApiReference() {
        return apiReference;
    }

    public String getName() {
        return name;
    }
}
