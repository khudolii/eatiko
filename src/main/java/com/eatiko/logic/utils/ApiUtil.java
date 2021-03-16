package com.eatiko.logic.utils;

import com.eatiko.logic.processors.RecipeProcessor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class ApiUtil {

    private static final Class<RecipeProcessor> CLAZZ = RecipeProcessor.class;
    private static final Logger logger = Logger.getLogger(CLAZZ);

    @Autowired
    private static OkHttpClient client;

    public static JSONObject sendGETRequestToApi(String requestURL) {
        try {
            Request request = new Request.Builder()
                    .url(requestURL)
                    .build();

            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (Exception e) {
            logger.error("sendGETRequestToApi: " + e);
            return null;
        }
    }
}
