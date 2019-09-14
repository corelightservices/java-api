package com.corelightservices.api;

import org.json.JSONObject;
import java.time.Instant;

public class CLSProject extends CLSObject {

    private String apiReference;
    private String name;
    private String description;
    private Instant timestampCreate;

    private CLSProject(){}

    static CLSProject create(JSONObject object) {
        CLSProject instance = new CLSProject();
        instance.apiReference = object.getString("apiReference");
        instance.name = object.getString("name");
        instance.description = object.getString("description");
        instance.timestampCreate = Utils.time(object.getString("timestampCreate"));
        return instance;
    }

    public String getApiReference() {
        return apiReference;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Instant getTimestampCreate() {
        return timestampCreate;
    }
}
