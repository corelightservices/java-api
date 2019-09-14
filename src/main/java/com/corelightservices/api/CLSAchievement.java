package com.corelightservices.api;

import org.json.JSONObject;

import java.time.Instant;

public class CLSAchievement extends CLSObject {

    private String name;
    private String description;
    private String icon;
    private boolean allowMultiple;
    private String type;
    private double targetProgress;
    private String apiReference;
    private Instant timestampCreate;

    private CLSAchievement() {}

    static CLSAchievement create(JSONObject object) {
        CLSAchievement instant = new CLSAchievement();
        instant.name = object.getString("name");
        instant.description = object.getString("description");
        instant.icon = object.getString("icon");
        instant.allowMultiple = object.getBoolean("allowMultiple");
        instant.type = object.getString("type");
        instant.targetProgress = object.getDouble("targetProgress");
        instant.apiReference = object.getString("apiReference");
        instant.timestampCreate = Utils.time(object.getString("timestampCreate"));
        return instant;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isAllowMultiple() {
        return allowMultiple;
    }

    public String getType() {
        return type;
    }

    public double getTargetProgress() {
        return targetProgress;
    }

    public String getApiReference() {
        return apiReference;
    }

    public Instant getTimestampCreate() {
        return timestampCreate;
    }
}
