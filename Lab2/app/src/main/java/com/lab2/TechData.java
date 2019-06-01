package com.lab2;

import org.json.JSONException;
import org.json.JSONObject;

public class TechData {

    private final String IMAGE_BASE_URL = "https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/";

    public String name = "";
    public String description = "";
    public String image_url = "";

    public TechData(JSONObject data) {
        try {
            image_url = IMAGE_BASE_URL + data.getString("graphic");
            name = data.getString("name");

            if (data.has("helptext")) {
                description = data.getString("helptext");
            }
        } catch (JSONException ignored) { }
    }
}
