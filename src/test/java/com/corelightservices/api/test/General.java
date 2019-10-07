package com.corelightservices.api.test;

import com.corelightservices.api.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

public class General extends CLSApiTest{
    @Test
    public void ValidateKey() throws CLSInvalidApiKeyException {
        CLSApiContext apiContext = new CLSApiContext(ApiKey);
        boolean result = apiContext.ValidateKey();
        Assert.assertTrue(result);
    }

    @Test
    public void GetProject() throws CLSApiException, IOException {
        CLSApiContext apiContext = new CLSApiContext(ApiKey);
        CLSProject project = apiContext.GetProject();
        Assert.assertEquals(ProjectReference, project.getProjectApiReference());
        Assert.assertEquals("CLS Test App", project.getName());
    }


    @Test
    public void PlayerRequests() throws CLSApiException, IOException {
        CLSApiContext apiContext = new CLSApiContext(ApiKey);

        CLSPlayer player = apiContext.RegisterPlayer();
        Assert.assertNotNull(player.getName());
        Assert.assertNotNull(player.getPlayerApiReference());
        Assert.assertNotNull(player.getPlayerKey());

        String playerKey = player.getPlayerKey();

        CLSPlayer player2 = apiContext.GetPlayer(player.getPlayerApiReference());
        Assert.assertEquals(player.getName(), player2.getName());
        Assert.assertEquals(player.getPlayerApiReference(), player2.getPlayerApiReference());
        Assert.assertNull(player2.getPlayerKey());

        String newName = "TestUser_" + UUID.randomUUID().toString();
        CLSPlayer player3 = apiContext.UpdatePlayer(player.getPlayerApiReference(), playerKey, newName);
        Assert.assertEquals(newName, player3.getName());
        Assert.assertEquals(player.getPlayerApiReference(), player3.getPlayerApiReference());
        Assert.assertNull(player3.getPlayerKey());
    }

    @Test
    public void GetPlayer() throws CLSApiException, IOException {
        CLSApiContext apiContext = new CLSApiContext(ApiKey);
        CLSPlayer player = apiContext.GetPlayer(PlayerReference);
        Assert.assertNotNull(player.getName());
        Assert.assertEquals(PlayerReference, player.getPlayerApiReference());
    }
}
