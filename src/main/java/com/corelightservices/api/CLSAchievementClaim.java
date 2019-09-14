package com.corelightservices.api;

import org.json.JSONObject;

import java.time.Instant;

public class CLSAchievementClaim extends CLSObject {

    private String playerApiReference;
    private String achievementApiReference;
    private double progress;
    private boolean done;
    private Instant updated;

    private CLSAchievementClaim() {}

    static CLSAchievementClaim create(JSONObject object) {
        CLSAchievementClaim instant = new CLSAchievementClaim();
        instant.playerApiReference = object.getString("playerReference");
        instant.achievementApiReference = object.getString("achievementReference");
        instant.progress = object.getDouble("progress");
        instant.done = object.getBoolean("done");
        instant.updated = Utils.time(object.getString("updated"));
        return instant;
    }

    public String getPlayerApiReference() {
        return playerApiReference;
    }

    public String getAchievementApiReference() {
        return achievementApiReference;
    }

    public double getProgress() {
        return progress;
    }

    public boolean isDone() {
        return done;
    }

    public Instant getUpdated() {
        return updated;
    }
}
