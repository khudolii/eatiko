package com.eatiko.logic.dto.factories;

import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.model.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class EdamamApiEntityFactory implements EntityFactory<Recipe, RecipeDTO> {

    @Autowired
    private RecipeDTO recipeDTO;

    @Override
    public Recipe getEntity(RecipeDTO recipeDTO) {
        return null;
    }

    @Override
    public RecipeDTO getDTO(Recipe recipe) {
        return null;
    }

    @Override
    public RecipeDTO createDTO(JSONArray json) {
        if (json == null || json.isEmpty()) {
            return null;
        }

       /* try {
            json.stream()..
            recipeDTO.setImage("tetst");
        }*/
        return recipeDTO;
    }

    public static void main(String[] args) throws IOException {
        /*URL url = new URL("");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = new BufferedInputStream(connection.getInputStream());*/
/*
        RecipeDTO recipeDTO = mapper.readValue(inputStream, RecipeDTO.class);
        System.out.println("recipeDTO.toString() = " + recipeDTO.toString());
*/

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.edamam.com/search?q=coffee&app_id=a0f76cd6&app_key=c4a78ec545b7354048d8feb0be603a4b&from=0&to=1")
                .build(); // defaults to GET

        Response response = client.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        JSONArray jsonArray = jsonObject.getJSONArray("hits");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            JSONObject recipe = obj.getJSONObject("recipe");
            ObjectMapper objectMapper = new ObjectMapper();

            System.out.println(objectMapper.readValue(recipe.toString(), RecipeDTO.class).toString());
        }
       // System.out.println(jsonObject.toString());
       // System.out.println(jsonObject.getJSONObject("recipe").toString());
        //System.out.println(response.body().string());
       // System.out.println("R_________________________________________");
        //System.out.println(mapper.readValue(response.body().byteStream(), RecipeDTO.class).toString());
    }

    private static JSONObject parseJSON(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonStr = sb.toString();
        JSONObject jObj = null;
        if (jsonStr.length() > 0) {
            jObj = new JSONObject(jsonStr);
        }
        return jObj;
    }
}
