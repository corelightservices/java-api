package com.corelightservices.api;

import org.json.JSONObject;

import java.time.Instant;

/**
 * Represents a CLS achievement claim.
 */
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

    /**
     * @return Reference of the player this claim belongs to
     */
    public String getPlayerApiReference() {
        return playerApiReference;
    }

    /**
     * @return Reference of the claimed achievement
     */
    public String getAchievementApiReference() {
        return achievementApiReference;
    }

    /**
     * @return Progress (only for progress achievements)
     */
    public double getProgress() {
        return progress;
    }

    /**
     * Should be only ever false for progress achievements where target has not been reached yet.
     * @return True, if achivement was awarded
     */
    public boolean isDone() {
        return done;
    }

    /**
     * @return Update timestamp
     */
    public Instant getUpdated() {
        return updated;
    }
}
