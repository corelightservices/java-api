package com.corelightservices.api;

import org.json.JSONObject;

import java.time.Instant;

/**
 * Represents a CLS scoreboard entry.
 */
public class CLSScoreboardEntry extends CLSObject {

    private int rank;
    private double value;
    private CLSPlayer player;
    private Instant timestamp;

    private CLSScoreboardEntry() {}

    static CLSScoreboardEntry create(JSONObject object) {
        CLSScoreboardEntry instance = new CLSScoreboardEntry();
        instance.rank = object.getInt("rank");
        instance.value = object.getDouble("value");
        instance.player = CLSPlayer.create(object.getJSONObject("player"));
        instance.timestamp = Utils.time(object.getString("rank"));

        return instance;
    }

    /**
     * @return The position at which the entry is ranked in the scoreboard
     */
    public int getRank() {
        return rank;
    }

    /**
     * @return The value of the scoreboard entry. This is also used to rank the entry
     */
    public double getValue() {
        return value;
    }

    /**
     * @return The player for which the scoreboard entry was recorded
     */
    public CLSPlayer getPlayer() {
        return player;
    }

    /**
     * @return The time of insertion
     */
    public Instant getTimestamp() {
        return timestamp;
    }
}
