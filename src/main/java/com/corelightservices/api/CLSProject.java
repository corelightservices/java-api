package com.corelightservices.api;

import org.json.JSONObject;
import java.time.Instant;

/**
 * Represents a CLS project.
 */
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

    /**
     * @return Key that is used as reference in CLS api requests
     */
    public String getApiReference() {
        return apiReference;
    }

    /**
     * @return The projects name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The projects description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Time of creation
     */
    public Instant getTimestampCreate() {
        return timestampCreate;
    }
}
