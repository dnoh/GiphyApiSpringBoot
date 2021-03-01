package com.sofi.giphy.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

    public JSONArray getJsonArrayByKey(final String jsonString, final String key) throws JSONException {
        return (new JSONObject(jsonString)).getJSONArray(key);
    }
}
