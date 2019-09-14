package com.corelightservices.api;

import org.json.JSONObject;

public class CLSScoreboard extends CLSObject {

    private String apiReference;
    private String name;
    private String description;

    private CLSScoreboard() {}

    static CLSScoreboard create(JSONObject object) {
        CLSScoreboard instance = new CLSScoreboard();
        instance.apiReference = object.getString("apiReference");
        instance.name = object.getString("name");
        instance.description = object.getString("description");
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
}
