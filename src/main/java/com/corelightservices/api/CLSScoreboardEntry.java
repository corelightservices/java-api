package com.corelightservices.api;

import org.json.JSONObject;

import java.time.Instant;

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

}
