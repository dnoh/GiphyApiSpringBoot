package com.sofi.giphy.util;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.Assert;

public class JsonUtilTest {

    private static final String JSON = "{\"todos\":[{\"id\":1,\"title\":\"Todo 1\",\"completed\":false},{\"id\":2,\"" +
            "title\":\"Todo 2\",\"completed\":false},{\"id\":3,\"title\":\"Todo 3\",\"completed\":true}]}";
    private static final String JSON_KEY_INCORRECT = "todos!";
    private static final String JSON_KEY_CORRECT = "todos";

    @SneakyThrows
    @Test(expected=JSONException.class)
    public void testGetJsonArrayByKeyException() {
        final JsonUtil jsonUtil = new JsonUtil();
        jsonUtil.getJsonArrayByKey(JSON, JSON_KEY_INCORRECT);
    }

    @SneakyThrows
    @Test
    public void testGetJsonArrayByKey() {
        final JsonUtil jsonUtil = new JsonUtil();
        final String jsonString = "[{\"id\":1,\"completed\":false,\"title\":\"Todo 1\"},{\"id\":2," +
                "\"completed\":false,\"title\":\"Todo 2\"},{\"id\":3,\"completed\":true,\"title\":\"Todo 3\"}]";
        final JSONArray jsonArray = new JSONArray(jsonString);
        final JSONArray jsonArrayByKey = jsonUtil.getJsonArrayByKey(JSON, JSON_KEY_CORRECT);
        for (int index = 0; index < jsonArrayByKey.length(); index++) {
            Assert.assertEquals(jsonArray.get(index).toString(), jsonArray.get(index).toString());
        }
    }

}
