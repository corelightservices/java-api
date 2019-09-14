package com.corelightservices.api;

import org.json.JSONObject;

/**
 * Represents a registered player.
 */
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

    /**
     * @return Key that is used as reference in CLS api requests
     */
    public String getApiReference() {
        return apiReference;
    }

    /**
     * @return The players displayed user name
     */
    public String getName() {
        return name;
    }
}
