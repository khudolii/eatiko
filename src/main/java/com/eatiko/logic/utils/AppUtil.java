package com.eatiko.logic.utils;

import com.eatiko.logic.processors.RecipeProcessor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class AppUtil {

    private static final Class<AppUtil> CLAZZ = AppUtil.class;
    private static final Logger logger = Logger.getLogger(CLAZZ);


    public static JSONObject sendGETRequestToApi(String requestURL) {
        try {
            OkHttpClient client = new OkHttpClient();
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
