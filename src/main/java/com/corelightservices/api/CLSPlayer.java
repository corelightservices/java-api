package com.corelightservices.api;

import org.json.JSONObject;

/**
 * Represents a registered player.
 */
public class CLSPlayer extends CLSObject {

    private String playerApiReference;
    private String name;
    private String playerKey;
    private String countryCode;

    private CLSPlayer() {}

    static CLSPlayer create(JSONObject object) {
        CLSPlayer instance = new CLSPlayer();
        instance.playerApiReference = object.getString("playerReference");
        instance.name = object.getString("name");
        if(object.has("playerKey"))
            instance.playerKey = object.getString("playerKey");
        if(object.has("countryCode"))
            instance.countryCode = object.getString("countryCode");
        return instance;
    }

    /**
     * @return Key that is used as reference in CLS api requests
     */
    public String getPlayerApiReference() {
        return playerApiReference;
    }

    /**
     * @return Key that is used to authenticate the player against the CLS api.
     */
    public String getPlayerKey() {
        return playerKey;
    }

    /**
     * @return The players displayed user name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The players country code
     */
    public String getCountryCode() {
        return countryCode;
    }
}
