package com.corelightservices.api;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Clock;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Helper class for executing api requests.
 * To instantiate CLSApiContext you will need an API Key.
 * You can generate API Keys for your project at <a href="https://corelightservices.com">corelightservices.com</a>.
 */
public class CLSApiContext {
    private static String baseUrl = "https://corelightservices.com/api/";

    private String apiKey;

    /**
     * Constructor
     * @param apiKey The projects API-Key
     * @throws CLSInvalidApiKeyException if the provided api key is not a valid key
     */
    public CLSApiContext(String apiKey) throws CLSInvalidApiKeyException {
        if (Utils.stringEmpty(apiKey) || apiKey.length() != 36) throw new CLSInvalidApiKeyException();
        this.apiKey = apiKey;
    }

    /**
     * Checks the API key for validity.
     * @return True if API key is valid
     */
    public boolean ValidateKey()
    {
        try
        {
            ApiRequest(baseUrl + "key", RequestJson());
            return true;
        }
        catch (Exception ignore)
        {
            return false;
        }
    }

    /**
     * Requests information about the project the API key was generated for.
     * @return The requestet project
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public CLSProject GetProject() throws IOException, CLSApiException {
        return ApiResultRequest("project", RequestJson(), "project", CLSProject::create);
    }

    /**
     * Requests information about a specific player.
     * @param playerApiReference The CLS player api-reference
     * @return The requestet player
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public CLSPlayer GetPlayer(String playerApiReference) throws IOException, CLSApiException {
        ValidateReference(playerApiReference, "playerApiReference");

        JSONObject json = RequestJson();
        json.put("PlayerReference", playerApiReference);

        return ApiResultRequest("player", json, "player", CLSPlayer::create);
    }

    /**
     * Registers a new player at CLS.
     * @return The registered player
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public CLSPlayer RegisterPlayer() throws IOException, CLSApiException {
        return RegisterPlayer(null);
    }

    /**
     * Registers a new player with username at CLS.
     * @param playerName The players user name
     * @return The registered player
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public CLSPlayer RegisterPlayer(String playerName) throws IOException, CLSApiException {
        JSONObject json = RequestJson();
        if (!Utils.stringEmpty(playerName))
            json.put("Name", playerName);

        return ApiResultRequest("player/register", json, "player", CLSPlayer::create);
    }

    /**
     * Updates player information at CLS.
     * @param playerApiReference The CLS player api-reference
     * @param playerKey The CLS player authentication key
     * @param playerName The players new user name
     * @return The updated player
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public CLSPlayer UpdatePlayer(String playerApiReference, String playerKey, String playerName) throws IOException, CLSApiException {
        ValidateReference(playerApiReference, "playerApiReference");

        JSONObject json = RequestJson();
        json.put("PlayerReference", playerApiReference);
        json.put("PlayerKey", playerKey);
        json.put("Name", playerName);

        return ApiResultRequest("player/update", json, "player", CLSPlayer::create);
    }

    /**
     * Updates player information at CLS.
     * The CLSPlayer object must contain valid api-reference and player key.
     * @param player The CLS player
     * @param playerName The players new user name
     * @return The updated player
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public CLSPlayer UpdatePlayer(CLSPlayer player, String playerName) throws IOException, CLSApiException {
        return UpdatePlayer(player.getPlayerApiReference(), player.getPlayerKey(), playerName);
    }

    /**
     * Requests scoreboard results.
     * @param scoreboardApiReference The CLS scoreboard api-reference
     * @param offset Defines the offset for the entry query (0 &lt;= offset)
     * @param limit Defines how many records should be queried (1 &lt;= limit &lt;= 1000)
     * @return The requestet scoreboard including it's records
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public CLSScoreboard GetScoreboardResults(String scoreboardApiReference, int offset, int limit) throws IOException, CLSApiException {
        ValidateReference(scoreboardApiReference, "scoreboardApiReference");

        if (offset < 0) throw new IllegalArgumentException("Offset must have a value greater or equal to 0.");
        if (limit < 1 || limit > 1000) throw new IllegalArgumentException("Limit must have a value between 1 and 1000.");

        JSONObject json = RequestJson();
        json.put("Offset", offset);
        json.put("Limit", limit);

        return ApiResultRequest("scoreboard/" + scoreboardApiReference + "/results", json, "scoreboard", CLSScoreboard::create);
    }

    /**
     * Requests scoreboard results for a specific player.
     * @param scoreboardApiReference The CLS scoreboard api-reference
     * @param playerApiReference The CLS player api-reference
     * @param offset Defines the offset for the entry query (0 &lt; offset)
     * @param limit Defines how many records should be queried (1 &lt;= limit &lt;= 1000)
     * @return The requestet scoreboard including it's records
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public CLSScoreboard GetScoreboardResults(String scoreboardApiReference, String playerApiReference, int offset, int limit) throws IOException, CLSApiException {
        ValidateReference(scoreboardApiReference, "scoreboardApiReference");
        ValidateReference(playerApiReference, "playerApiReference");

        if (offset < 0) throw new IllegalArgumentException("Offset must have a value greater or equal to 0.");
        if (limit < 1 || limit > 1000) throw new IllegalArgumentException("Limit must have a value between 1 and 1000.");

        JSONObject json = RequestJson();
        json.put("PlayerReference", playerApiReference);
        json.put("Offset", offset);
        json.put("Limit", limit);

        return ApiResultRequest("scoreboard/" + scoreboardApiReference + "/results", json, "scoreboard", CLSScoreboard::create);
    }

    /**
     * Requests information about a specific scoreboard.
     * @param scoreboardApiReference The CLS scoreboard api-reference
     * @return The requestet scoreboard
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public CLSScoreboard GetScoreboard(String scoreboardApiReference) throws IOException, CLSApiException {
        ValidateReference(scoreboardApiReference, "scoreboardApiReference");

        JSONObject json = RequestJson();
        json.put("ScoreboardReference", scoreboardApiReference);

        return ApiResultRequest("scoreboard", json, "scoreboard", CLSScoreboard::create);
    }

    /**
     * Records a new scoreboard entry.
     * The CLSPlayer object must contain valid api-reference and player key.
     * @param scoreboardApiReference The CLS scoreboard api-reference
     * @param player The CLS player that should be referenced by the record
     * @param value The value of the scoreboard entry (eg. the players score)
     * @return The created scoreboard entry
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public CLSScoreboardEntry InsertScoreboardRecord(String scoreboardApiReference, CLSPlayer player, double value) throws IOException, CLSApiException {
        if (player == null) throw new NullPointerException("player must not be null");
        return InsertScoreboardRecord(scoreboardApiReference, player.getPlayerApiReference(), player.getPlayerKey(), value);
    }

    /**
     * Records a new scoreboard entry.
     * @param scoreboardApiReference The CLS scoreboard api-reference
     * @param playerApiReference The api-reference of the CLS player that should be referenced by the record
     * @param playerKey The CLS player authentication key
     * @param value The value of the scoreboard entry (eg. the players score)
     * @return The created scoreboard entry
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public CLSScoreboardEntry InsertScoreboardRecord(String scoreboardApiReference, String playerApiReference, String playerKey, double value) throws IOException, CLSApiException {
        ValidateReference(scoreboardApiReference, "scoreboardApiReference");
        ValidateReference(playerApiReference, "playerApiReference");

        JSONObject json = RequestJson();
        json.put("PlayerReference", playerApiReference);
        json.put("PlayerKey", playerKey);
        json.put("Value", value);
        json.put("Timestamp", Clock.systemUTC().instant().toString());

        return ApiResultRequest("scoreboard/" + scoreboardApiReference + "/insert", json, "entry", CLSScoreboardEntry::create);
    }

    /**
     * Requests information about a specific achievement.
     * @param achievementApiReference The CLS achievement api-reference
     * @return The requestet achievement
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public CLSAchievement GetAchievement(String achievementApiReference) throws IOException, CLSApiException {
        ValidateReference(achievementApiReference, "achievementApiReference");

        JSONObject json = RequestJson();
        json.put("AchievementReference", achievementApiReference);

        return ApiResultRequest("achievement", json, "achievement", CLSAchievement::create);
    }

    /**
     * Requests information about all achievements.
     * @return The requestet achievement
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public List<CLSAchievement> GetAchievements() throws IOException, CLSApiException {
        JSONObject json = RequestJson();
        return ApiResultArrayRequest("achievement", json, "achievements", CLSAchievement::create);
    }

    /**
     * Convenience overload for GetAchievementClaims(playerApiReference, null)
     * @param playerApiReference The CLS player api-reference
     * @return The requestet achievement claims
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public  List<CLSAchievementClaim> GetAchievementClaims(String playerApiReference) throws IOException, CLSApiException {
        return GetAchievementClaims(playerApiReference, null);
    }

    /**
     * Requests achievement claims for a specific player.
     * When achievementApiReference is null, claims for all achievements are retrieved.
     * @param playerApiReference The CLS player api-reference
     * @param achievementApiReference The CLS achievement api-reference
     * @return The requestet achievement claims
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public  List<CLSAchievementClaim> GetAchievementClaims(String playerApiReference, String achievementApiReference) throws IOException, CLSApiException {
        ValidateReference(playerApiReference, "playerApiReference");
        if (achievementApiReference != null)
            ValidateReference(achievementApiReference, "achievementApiReference");

        JSONObject json = RequestJson();
        json.put("PlayerReference", playerApiReference);

        if (achievementApiReference != null)
            json.put("AchievementReference", achievementApiReference);

        return ApiResultArrayRequest("achievement/claims", json, "claims", CLSAchievementClaim::create);
    }

    /**
     * Convenience overload for ClaimAchievement(playerApiReference, achievementApiReference, null)
     * @param playerApiReference The CLS player api-reference
     * @param playerKey The CLS player authentication key
     * @param achievementApiReference The CLS achievement api-reference
     * @return The requestet achievement claim
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public  CLSAchievementClaim ClaimAchievement(String playerApiReference, String playerKey, String achievementApiReference) throws IOException, CLSApiException {
        return ClaimAchievement(playerApiReference, playerKey, achievementApiReference, null);
    }

    /**
     * Awards an event achievement to a player or updates progress on a progress achievement.
     * @param playerApiReference The CLS player api-reference
     * @param playerKey The CLS player authentication key
     * @param achievementApiReference The CLS achievement api-reference
     * @param progress The progress that will be added to the current achievement progress <i>(should be null for event achievements and greater than 0 for progress achievements)</i>
     * @return The requestet achievement claim
     * @throws IOException if api request failed on a network level
     * @throws CLSApiException if the api reports an error or returns an invalid response
     */
    public CLSAchievementClaim ClaimAchievement(String playerApiReference, String playerKey, String achievementApiReference, Double progress) throws IOException, CLSApiException {
        ValidateReference(playerApiReference, "playerApiReference");
        ValidateReference(achievementApiReference, "achievementApiReference");

        JSONObject json = RequestJson();
        json.put("PlayerReference", playerApiReference);
        json.put("PlayerKey", playerKey);
        json.put("AchievementReference", achievementApiReference);
        if(progress != null)
            json.put("Progress", progress);

        return ApiResultRequest("achievement/claim", json, "claim", CLSAchievementClaim::create);
    }

    private JSONObject RequestJson()
    {
        JSONObject json = new JSONObject();
        json.put("ApiKey", apiKey);
        return json;
    }

    private <CLSResult extends CLSObject> CLSResult ApiResultRequest(String partialUrl, JSONObject requestJson, String resultKey, Function<JSONObject, CLSObject> creator) throws CLSApiException, IOException {
        JSONObject result = ApiRequest(baseUrl + partialUrl, requestJson);
        if (!result.has(resultKey)) throw new CLSInvalidResponseException();
        JSONObject resultJson = result.getJSONObject(resultKey);

        try
        {
            //noinspection unchecked
            return (CLSResult) creator.apply(resultJson);
        }
        catch (Exception ignore)
        {
            throw new CLSInvalidResponseException();
        }
    }

    private <CLSResult extends CLSObject> List<CLSResult> ApiResultArrayRequest(String partialUrl, JSONObject requestJson, String resultKey, Function<JSONObject, CLSObject> creator) throws IOException, CLSApiException {
        JSONObject result = ApiRequest(baseUrl + partialUrl, requestJson);
        if (!result.has(resultKey)) throw new CLSInvalidResponseException();
        JSONArray resultJson = result.getJSONArray(resultKey);

        try
        {
            //noinspection unchecked
            return resultJson.toList().stream()
                    .filter(c -> c instanceof JSONObject)
                    .map(c -> (CLSResult) creator.apply((JSONObject)c))
                    .collect(Collectors.toList());
        }
        catch(Exception ignore)
        {
            throw new CLSInvalidResponseException();
        }
    }

    private static JSONObject ApiRequest(String url, JSONObject content) throws CLSApiException, IOException {
        try(CloseableHttpClient client = HttpClientBuilder.create().build()) {
            StringEntity requestEntity = new StringEntity(
                    content.toString(),
                    ContentType.APPLICATION_JSON);

            HttpPost postMethod = new HttpPost(url);
            postMethod.setEntity(requestEntity);

            HttpResponse rawResponse = client.execute(postMethod);
            if (rawResponse.getStatusLine().getStatusCode() != 200)
                throw new CLSInvalidResponseException();

            HttpEntity entity = rawResponse.getEntity();
            JSONObject resultJson = new JSONObject(IOUtils.toString(entity.getContent(), Charset.forName("UTF-8")));
            if (resultJson.has("success") && !resultJson.getBoolean("success")) {
                if (resultJson.has("error"))
                    throw ParseErrorException(resultJson.getString("error"));

                throw new CLSApiException("API request failed.");
            }

            return resultJson;
        }
    }

    private static void ValidateReference(String apiReference, String referenceName)
    {
        if (Utils.stringEmpty(apiReference)) throw new IllegalArgumentException("ApiReference '"+referenceName+"' must be non null or whitespace.");
        try {
            //noinspection ResultOfMethodCallIgnored
            UUID.fromString(apiReference);
        }catch (IllegalArgumentException ignore) {
            throw new IllegalArgumentException("ApiReference '"+referenceName+"' is not valid.");
        }
    }

    private static CLSApiException ParseErrorException(String error) {
        if (error.equalsIgnoreCase("invalid api-key"))
            return new CLSInvalidApiKeyException();

        if (error.equalsIgnoreCase("invalid api-reference"))
            return new CLSInvalidReferenceException();

        if (error.equalsIgnoreCase("internal error"))
            return new CLSInternalErrorException();

        return new CLSApiException(error);
    }
}
