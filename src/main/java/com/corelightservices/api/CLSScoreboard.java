package com.corelightservices.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a CLS scoreboard.
 */
public class CLSScoreboard extends CLSObject {

    private String scoreboardApiReference;
    private String name;
    private String description;
    private int totalEntries;
    private Boolean playerUnique;
    private List<CLSScoreboardEntry> entries;

    private CLSScoreboard() {}

    static CLSScoreboard create(JSONObject object) {
        CLSScoreboard instance = new CLSScoreboard();
        instance.scoreboardApiReference = object.getString("scoreboardReference");
        instance.name = object.getString("name");
        instance.description = object.getString("description");
        instance.totalEntries = object.getInt("totalEntries");
        instance.playerUnique = object.getBoolean("playerUnique");
        JSONArray entries = object.getJSONArray("entries");
        instance.entries = entries.toList().stream().map(e->CLSScoreboardEntry.create((JSONObject) e)).collect(Collectors.toList());
        return instance;
    }

    /**
     * @return Key that is used as reference in CLS api requests
     */
    public String getScoreboardApiReference() {
        return scoreboardApiReference;
    }

    /**
     * @return The scoreboards name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The scoreboards description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The count of total entries in this scoreboard
     */
    public int getTotalEntries() {
        return totalEntries;
    }

    /**
     * @return True, if entries are unique per player
     */
    public Boolean getPlayerUnique() {
        return playerUnique;
    }

    /**
     * @return List of scoreboard entries
     */
    public List<CLSScoreboardEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }
}
