package com.example.testtask.helpers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GenerateRandomAccountsTest {

    @Test
    public void all_fields_filled() {
        List<JSONObject> accounts = GenerateRandomAccounts.getRandomUserDetails();

        for (JSONObject item : accounts) {
            try {
                Object name = item.get("name");
                Object price = item.get("price");
                Object description = item.get("description");
                //Object test = item.get("test");
                assertNotEquals(name, "");
                assertNotEquals(price, "");
                assertNotEquals(description, "");
                //assertNotEquals(test, "");
            } catch (JSONException ex) {
                Assert.fail(ex.getMessage());
            }
        }
    }

    @Test
    public void size_correct() {
        List<JSONObject> accounts = GenerateRandomAccounts.getRandomUserDetails();
        assertEquals(accounts.size(), 30);
    }
}