package com.example.testtask;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GenerateRandomDetails {

    private static final Random RANDOM = new Random();

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = RANDOM.nextInt(Objects.requireNonNull(clazz.getEnumConstants()).length);
        return clazz.getEnumConstants()[x];
    }

    public static List<JSONObject> getRandomUserDetails() {

        try {
            List<JSONObject> result = new ArrayList<>();
            int size = 12;
            for (int i = 0; i < size; i++) {
                JSONObject userJson = new JSONObject();
                userJson.put("id", RandomId());
                userJson.put("firstName", randomString(10));
                userJson.put("lastName", randomString(8));

                JSONArray detailsJson = new JSONArray();
                for (int j = 0; j < size / 2; j++) {
                    JSONObject item = new JSONObject();
                    item.put("currency", randomEnum(DetailData.MyCurrency.class).toString());
                    item.put("sum", randomPrice());
                    detailsJson.put(item);
                }

                userJson.put("details", detailsJson);

                result.add(userJson);
            }

            return result;
        } catch (JSONException ex) {
            Log.d("GenerateRandomDetails", "Здесь не может быть ошибок");
            return new ArrayList<>();
        }
    }

    private static float randomPrice() {
        float leftLimit = 10F;
        float rightLimit = 1000F;
        return leftLimit + RANDOM.nextFloat() * (rightLimit - leftLimit);
    }

    private static Integer UniqueId = 1;

    private static Integer RandomId() {
        return UniqueId++;
    }

    private static String randomString(int size) {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        StringBuilder buffer = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int randomLimitedInt = leftLimit + (int)
                (RANDOM.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }
}
