package com.corelightservices.api.test;

import com.corelightservices.api.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

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
        Assert.assertEquals(ProjectReference, project.getApiReference());
        Assert.assertEquals("CLS Test App", project.getName());
    }


    @Test
    public void RegisterPlayer() throws CLSApiException, IOException {
        CLSApiContext apiContext = new CLSApiContext(ApiKey);
        try
        {
            String userName = "TestUserName";
            CLSPlayer player = apiContext.RegisterPlayer(userName);
            if (userName != null)
                Assert.assertEquals(userName, player.getName());
            else
                Assert.assertNotNull(player.getName());
        }
        catch (CLSApiException exception)
        {
            if (!exception.getMessage().contains("already exists"))
                throw exception;
        }
    }

    @Test
    public void GetPlayer() throws CLSApiException, IOException {
        CLSApiContext apiContext = new CLSApiContext(ApiKey);
        CLSPlayer player = apiContext.GetPlayer(PlayerReference);
        Assert.assertNotNull(player.getName());
        Assert.assertEquals(PlayerReference, player.getApiReference());
    }
}
