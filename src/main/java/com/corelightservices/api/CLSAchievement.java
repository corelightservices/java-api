package com.corelightservices.api;

import org.json.JSONObject;

import java.time.Instant;

/**
 * Represents a CLS achievement.
 */
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

    /**
     * @return The achievements name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The achievements description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The achievements icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @return True, if this achievement can be awarded multiple times
     */
    public boolean isAllowMultiple() {
        return allowMultiple;
    }

    /**
     * @return Achievement type (Event / Progress)
     */
    public String getType() {
        return type;
    }

    /**
     * @return Progress goal for progress achievements
     */
    public double getTargetProgress() {
        return targetProgress;
    }

    /**
     * @return Key that is used as reference in CLS api requests.
     */
    public String getApiReference() {
        return apiReference;
    }

    /**
     * @return Time of creation
     */
    public Instant getTimestampCreate() {
        return timestampCreate;
    }
}
