package com.eatiko.logic.api;

import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.processors.RecipeProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EdamamAPI implements BaseAPI<RecipeDTO> {

    private static final Class<RecipeProcessor> CLAZZ = RecipeProcessor.class;
    private static final Logger logger = Logger.getLogger(CLAZZ);

    private static final String BASE_URI = "https://api.edamam.com/search";
    private static final String TOKEN_KEY = "c4a78ec545b7354048d8feb0be603a4b";
    private static final String APP_API_ID = "a0f76cd6";

    @Override
    public String getApiURL() {
        return BASE_URI +
                "?" +
                "app_id=" +
                APP_API_ID +
                "&app_key=" +
                TOKEN_KEY;
    }

    @Override
    public String getApiRequestWithParameter(String parameter) {
        String url = getApiURL();
        return url + "&q=" +
                parameter;
    }

    @Override
    public String getApiRequestWithParameters(List<String> entities) {
        return null;
    }


    @Override
    public List<RecipeDTO> createDTOListByJSON(JSONObject json) {
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        JSONArray jsonArray = json.getJSONArray("hits");
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONObject recipe = obj.getJSONObject("recipe");
                ObjectMapper objectMapper = new ObjectMapper();

                RecipeDTO recipeDTO = objectMapper.readValue(recipe.toString(), RecipeDTO.class);
                if (recipeDTO != null) {
                    recipeDTOList.add(recipeDTO);
                }
            } catch (Exception e) {
                logger.error("createDTOListByJSON: " + e);
            }
        }
        return recipeDTOList;
    }
}
