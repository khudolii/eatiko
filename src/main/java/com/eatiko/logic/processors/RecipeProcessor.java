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
import org.springframework.stereotype.Component;

import java.util.*;

import org.apache.log4j.Logger;

@Component
public class RecipeProcessor {
    private static final Class<RecipeProcessor> CLAZZ = RecipeProcessor.class;
    private static final Logger logger = Logger.getLogger(CLAZZ);
    private static boolean isRunning = false;

    private RecipeService recipeService;
    private IngredientService ingredientService;
    private ProductService productService;

    @Autowired
    public RecipeProcessor(RecipeService recipeService, IngredientService ingredientService, ProductService productService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.productService = productService;
    }

    //@Scheduled(fixedDelay = 5000)
    public void startParsingRecipes() {
        if (isRunning) {
            logger.info("RecipeProcessor is running! Next iteration will start later");
        } else {
            isRunning = true;
            List<BaseAPI<RecipeDTO>> apisList = new ArrayList<>();
            try {
                List<Product> products = productService.getAllProducts();
                //products.forEach(System.out::println);
               // products.add(new Product("apple"));
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
                                    .map(recipeDTO -> recipeService.createRecipe(recipeDTO))
                                    .forEachOrdered(recipe -> {
                                        List<Ingredient> ingredients = recipe.getIngredients();
                                        ingredients.forEach(_ingredient -> {
                                            Product p = getProductByIngredient(products, _ingredient);
                                            _ingredient.setProductId(p == null ? 0L : p.getProductId());
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

/*    public static void main(String[] args) {
        String s = "2 1/2 #$cups #$grappa34 or unflavored vodka";
        String test = s.replaceAll("[^A-Za-zА-Яа-я]", "").toLowerCase(Locale.ROOT);
        System.out.println(test);
        String word = "vodka".toLowerCase(Locale.ROOT);
        int charCount = 0;
        List<String>
        for (int j = 0; j < test.length(); j++) {

                if (test.charAt(j) == word.charAt(charCount)) {

            }

        }

    }*/

}
