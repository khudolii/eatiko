package com.eatiko.logic.processors;

import com.eatiko.logic.api.BaseAPI;
import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.model.Product;
import com.eatiko.logic.model.Recipe;
import com.eatiko.logic.services.RecipeService;
import com.eatiko.logic.utils.ApiUtil;
import org.json.JSONObject;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import org.apache.log4j.Logger;

@Component
public class RecipeProcessor {
    private static final Class<RecipeProcessor> CLAZZ = RecipeProcessor.class;
    private static final Logger logger = Logger.getLogger(CLAZZ);
    private static boolean isRunning = false;

    private RecipeService recipeService;

    @Autowired
    public RecipeProcessor(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    //@Scheduled(fixedDelay = 5000)
    public void startParsingRecipes() {
        if (isRunning) {
            logger.info("RecipeProcessor is running! Next iteration will start later");
        } else {
            isRunning = true;
            List<BaseAPI<RecipeDTO>> apisList = new ArrayList<>();
            try {
                Set<Product> products = new HashSet<>(); //TODO add getProduct method
                products.add(new Product("coffee"));
                //products.add(new Product("chicken"));
                Set<String> recipesNames = new HashSet<>();
                if (products == null || products.isEmpty()) {
                    return;
                }

                Reflections reflections = new Reflections("com.eatiko.logic.api");
                Set<Class<? extends BaseAPI>> classes = reflections.getSubTypesOf(BaseAPI.class);

                for (Class<? extends BaseAPI> api : classes) {
                    BaseAPI<RecipeDTO> baseAPI = api.newInstance();
                    apisList.add(baseAPI);
                }

                if (!apisList.isEmpty()) {
                    apisList.forEach(_api -> toParsingRecipes(_api, products, recipesNames));
                } else {
                    logger.error("Not found BaseApis classes in package");

                }
            } catch (Exception e) {
                logger.error("startParsingRecipes: " + e);
            } finally {
                isRunning = false;
            }
        }
    }

    private void toParsingRecipes(BaseAPI<RecipeDTO> api, Set<Product> products, Set<String> recipesNames) {
        for (Product _product : products) {
            try {
                String request = api.getApiRequestWithParameter(_product.getName());
                if (request != null && !request.isEmpty()) {
                    JSONObject jsonObject = ApiUtil.sendGETRequestToApi(request);
                    if (jsonObject != null && !jsonObject.isEmpty()) {
                        List<RecipeDTO> recipeDTOList = api.createDTOListByJSON(jsonObject);
                        if (recipeDTOList != null && !recipeDTOList.isEmpty()) {
                            for (RecipeDTO recipeDTO : recipeDTOList) {
                                if (recipeDTO.getName() != null && !recipesNames.contains(recipeDTO.getName())) {
                                    Recipe recipe = recipeService.createRecipe(recipeDTO);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("toParsingRecipes: " + e);
            }
        }
    }

}
