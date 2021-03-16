package com.eatiko.logic.processors;

import com.eatiko.logic.api.BaseAPI;
import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.model.Product;
import com.eatiko.logic.utils.ApiUtil;
import org.json.JSONObject;
import org.reflections.Reflections;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

import org.apache.log4j.Logger;

@Component
public class RecipeProcessor {
    private static final Class<RecipeProcessor> CLAZZ = RecipeProcessor.class;
    private static final Logger logger = Logger.getLogger(CLAZZ);
    private static boolean isRunning = false;

    @Scheduled(fixedDelay = 5000)
    public void startParsingRecipes(){
        if (isRunning) {
            logger.info("RecipeProcessor is running! Next iteration will start later");
        } else {
            isRunning = true;
            List<BaseAPI> apisList = new ArrayList<>();
            try {
                Set<Product> products = new HashSet<>(); //TODO add getProduct method
                if (products == null || products.isEmpty()) {
                    return;
                }

                Reflections reflections = new Reflections("com.eatiko.logic.api");
                Set<Class<? extends BaseAPI>> classes = reflections.getSubTypesOf(BaseAPI.class);

                for (Class<? extends BaseAPI> api : classes) {
                    BaseAPI baseAPI = api.newInstance();
                    apisList.add(baseAPI);
                }

                if (!apisList.isEmpty()) {
                    apisList.forEach(_api -> toParsingRecipes(_api, products));
                } else {
                    logger.error("Not found BaseApis classes in package");

                }
            } catch (Exception e) {
                logger.error("startParsingRecipes: " + e);
            }
            finally {
                isRunning = false;
            }
        }
    }

    private void toParsingRecipes(BaseAPI api, Set<Product> products) {
        for(Product _product : products){
            try {
                String request = api.getApiRequestWithParameter(_product);
                if (request != null && !request.isEmpty()) {
                    JSONObject jsonObject = ApiUtil.sendGETRequestToApi(request);
                    if (jsonObject != null && !jsonObject.isEmpty()) {
                       List<RecipeDTO> recipeDTOList = api.createDTOListByJSON(jsonObject);
                    }
                }
            } catch (Exception e){
                logger.error("toParsingRecipes: " + e);
            }
        }
    }

}
