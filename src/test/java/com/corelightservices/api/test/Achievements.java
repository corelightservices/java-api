package com.corelightservices.api.test;

import com.corelightservices.api.CLSAchievement;
import com.corelightservices.api.CLSAchievementClaim;
import com.corelightservices.api.CLSApiContext;
import com.corelightservices.api.CLSApiException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class Achievements extends CLSApiTest {
    public final String EventReference = "## Event Achievement API Reference ##";
    public final String EventMultipleReference = "## Multiple Event Achievement API Reference ##";
    public final String ProgressReference = "## Progress Achievement API Reference ##";
    public final String ProgressMultipleReference = "## Multiple Progress Achievement API Reference ##";

    private final double dDelta = 0.000001;

    @Test
    public void GetAllAchievements() throws CLSApiException, IOException {
        CLSApiContext apiContext = new CLSApiContext(ApiKey);

        List<CLSAchievement> achievements = apiContext.GetAchievements();
        for (CLSAchievement achievement : achievements)
        {
            Assert.assertNotNull(achievement.getName());
            Assert.assertNotNull(achievement.getType());
            if (achievement.getType().equals("Progress"))
            {
                Assert.assertNotEquals(0, achievement.getTargetProgress());
            }
        }
    }

    @Test
    public void GetAchievementEvent() throws CLSApiException, IOException {
        CLSApiContext apiContext = new CLSApiContext(ApiKey);

        CLSAchievement achievement = apiContext.GetAchievement(EventReference);
        Assert.assertEquals("Event", achievement.getName());
        Assert.assertEquals("Event", achievement.getType());
        Assert.assertFalse(achievement.isAllowMultiple());
        Assert.assertEquals(0, achievement.getTargetProgress(), dDelta);
    }

    @Test
    public void GetAchievementEventMultiple() throws CLSApiException, IOException {
        CLSApiContext apiContext = new CLSApiContext(ApiKey);

        CLSAchievement achievement = apiContext.GetAchievement(EventMultipleReference);
        Assert.assertEquals("EventMultiple", achievement.getName());
        Assert.assertEquals("Event", achievement.getType());
        Assert.assertTrue(achievement.isAllowMultiple());
        Assert.assertEquals(0, achievement.getTargetProgress(), dDelta);
    }

    @Test
    public void GetAchievementProgress() throws CLSApiException, IOException {
        CLSApiContext apiContext = new CLSApiContext(ApiKey);

        CLSAchievement achievement = apiContext.GetAchievement(ProgressReference);
        Assert.assertEquals("Progress", achievement.getName());
        Assert.assertEquals("Progress", achievement.getType());
        Assert.assertFalse(achievement.isAllowMultiple());
        Assert.assertEquals(100, achievement.getTargetProgress(), dDelta);
    }

    @Test
    public void GetAchievementProgressMultiple() throws CLSApiException, IOException {
        CLSApiContext apiContext = new CLSApiContext(ApiKey);

        CLSAchievement achievement = apiContext.GetAchievement(ProgressMultipleReference);
        Assert.assertEquals("ProgressMultiple", achievement.getName());
        Assert.assertEquals("Progress", achievement.getType());
        Assert.assertTrue(achievement.isAllowMultiple());
        Assert.assertEquals(5, achievement.getTargetProgress(), dDelta);
    }

    @Test
    public void ClaimAchievement() throws CLSApiException, IOException {
        CLSApiContext apiContext = new CLSApiContext(ApiKey);
        CLSAchievementClaim claim;

        try
        {
            claim = apiContext.ClaimAchievement(PlayerReference, PlayerKey, EventReference);
            Assert.assertNotNull(claim);
            Assert.assertEquals(PlayerReference, claim.getPlayerApiReference());
            Assert.assertEquals(EventReference, claim.getAchievementApiReference());
        }
        catch (IOException exception)
        {
            if (!exception.getMessage().contains("already awarded"))
                throw exception;
        }

        claim = apiContext.ClaimAchievement(PlayerReference, PlayerKey, EventMultipleReference);
        Assert.assertNotNull(claim);
        Assert.assertEquals(PlayerReference, claim.getPlayerApiReference());
        Assert.assertEquals(EventMultipleReference, claim.getAchievementApiReference());

        try
        {
            claim = apiContext.ClaimAchievement(PlayerReference, PlayerKey, ProgressReference, 1.0);
            Assert.assertNotNull(claim);
            Assert.assertEquals(PlayerReference, claim.getPlayerApiReference());
            Assert.assertEquals(ProgressReference, claim.getAchievementApiReference());
        }
        catch (IOException exception)
        {
            if (!exception.getMessage().contains("already awarded"))
                throw exception;
        }

        claim = apiContext.ClaimAchievement(PlayerReference, PlayerKey, ProgressMultipleReference, 1.0);
        Assert.assertNotNull(claim);
        Assert.assertEquals(PlayerReference, claim.getPlayerApiReference());
        Assert.assertEquals(ProgressMultipleReference, claim.getAchievementApiReference());
    }

    @Test
    public void GetAchievementClaims() throws CLSApiException, IOException {
        CLSApiContext apiContext = new CLSApiContext(ApiKey);

        List<CLSAchievementClaim> claims = apiContext.GetAchievementClaims(PlayerReference);
        Assert.assertNotEquals(claims.size(), 0);

        for(CLSAchievementClaim claim : claims)
            Assert.assertEquals(PlayerReference, claim.getPlayerApiReference());
    }
}
