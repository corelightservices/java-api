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

    private String apiReference;
    private String name;
    private String description;
    private List<CLSScoreboardEntry> entries;

    private CLSScoreboard() {}

    static CLSScoreboard create(JSONObject object) {
        CLSScoreboard instance = new CLSScoreboard();
        instance.apiReference = object.getString("apiReference");
        instance.name = object.getString("name");
        instance.description = object.getString("description");
        JSONArray entries = object.getJSONArray("entries");
        instance.entries = entries.toList().stream().map(e->CLSScoreboardEntry.create((JSONObject) e)).collect(Collectors.toList());
        return instance;
    }

    /**
     * @return Key that is used as reference in CLS api requests
     */
    public String getApiReference() {
        return apiReference;
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
     * @return List of scoreboard entries
     */
    public List<CLSScoreboardEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }
}
