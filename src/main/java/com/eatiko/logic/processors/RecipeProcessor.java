package com.eatiko.logic.processors;

import com.eatiko.logic.api.BaseAPI;
import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.model.Ingredient;
import com.eatiko.logic.model.Product;
import com.eatiko.logic.services.IngredientService;
import com.eatiko.logic.services.ProductService;
import com.eatiko.logic.services.RecipeService;
import com.eatiko.logic.utils.AppUtil;
import org.json.JSONObject;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.util.ObjectUtils;

@Component
public class RecipeProcessor {
    private static final Class<RecipeProcessor> CLAZZ = RecipeProcessor.class;
    private static final Logger logger = Logger.getLogger(CLAZZ);
    private static boolean isRunning = false;

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final ProductService productService;

    @Autowired
    public RecipeProcessor(RecipeService recipeService, IngredientService ingredientService, ProductService productService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.productService = productService;
    }

    @Scheduled(fixedDelay = 5000)
    public void startParsingRecipes() {
        if (isRunning) {
            logger.info("RecipeProcessor is running! Next iteration will start later");
            System.out.println("PROCESSOR IN RUN");
        } else {
            System.out.println("PROCESSOR START");
            isRunning = true;
            List<BaseAPI<RecipeDTO>> apisList = new ArrayList<>();
            try {
                List<Product> products = productService.getAllProducts();
                Set<String> recipesNames = recipeService.getAllRecipesName();
                logger.info("Found " + products.size() + " products, and recipes - " + recipesNames.size());
                if (products == null || products.isEmpty()) {
                    logger.error("Not found any products");
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

    private void toParsingRecipes(BaseAPI<RecipeDTO> api, List<Product> products, Set<String> recipesNames) {
        for (Product _product : products) {
            try {
                String request = api.getApiRequestWithParameter(_product.getName());
                if (request != null && !request.isEmpty()) {
                    JSONObject jsonObject = AppUtil.sendGETRequestToApi(request);
                    if (jsonObject != null && !jsonObject.isEmpty()) {
                        List<RecipeDTO> recipeDTOList = api.createDTOListByJSON(jsonObject);
                        if (recipeDTOList != null && !recipeDTOList.isEmpty()) {

                            recipeDTOList.stream()
                                    .filter(recipeDTO -> recipeDTO.getName() != null
                                            && !recipesNames.contains(recipeDTO.getName()))
                                    .map(recipeService::createRecipe)
                                    .forEachOrdered(recipe -> {

                                        List<Ingredient> ingredients = recipe.getIngredients();
                                                                              ingredients.forEach(_ingredient -> {
                                            logger.info("Set ingredient - " + _ingredient.getName() + " to recipe - " + recipe.getRecipeName());
                                            Product ingredientProduct = getProductByIngredient(products, _ingredient);
                                            if (ObjectUtils.isEmpty(ingredientProduct)) {
                                                Product notFoundProduct = new Product();
                                                notFoundProduct.setProductId(0L);
                                                _ingredient.setProduct(notFoundProduct);
                                            } else {
                                                _ingredient.setProduct(ingredientProduct);

                                                if (ObjectUtils.isEmpty(ingredientProduct.getImgUrl())) {
                                                    String productUrl = _ingredient.getImageURL();

                                                    logger.info("Set img (" + productUrl + ") to product - " + ingredientProduct.getName());
                                                    if (!ObjectUtils.isEmpty(productUrl)) {
                                                        ingredientProduct.setImgUrl(productUrl);
                                                        productService.updateProduct(ingredientProduct);
                                                    }
                                                }
                                            }
                                            _ingredient.setRecipe(recipe);
                                            ingredientService.createIngredient(_ingredient);
                                        });
                                    });
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("toParsingRecipes: " + e);
            }
        }
    }

    private Product getProductByIngredient(List<Product> products, Ingredient ingredient) {
        String ingredientString = ingredient.getName();
        if (ingredientString == null || ingredientString.isEmpty()) {
            return null;
        }
        ingredientString = ingredientString
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");
        logger.info("ingredientString: " + ingredientString);
        for (Product _product : products) {
            String productName = _product.getName()
                    .toLowerCase(Locale.ROOT)
                    .replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");
            logger.info("productName: " + productName);
            if (ingredientString.contains(productName)) {
                logger.info("FOUND!!!");
                return _product;
            }
        }
        return null;
    }
}
