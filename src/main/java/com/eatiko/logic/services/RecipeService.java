package com.eatiko.logic.services;

import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.facade.FridgeFacade;
import com.eatiko.logic.facade.RecipeFacade;
import com.eatiko.logic.model.*;
import com.eatiko.logic.repository.RecipeRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private static final Class<RecipeService> CLAZZ = RecipeService.class;
    private static final Logger logger = Logger.getLogger(CLAZZ);

    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final RecipeFacade recipeFacade;
    private final FridgeService fridgeService;
    private final FridgeProductService fridgeProductService;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, IngredientService ingredientService,
                         RecipeFacade recipeFacade, FridgeService fridgeService, FridgeProductService fridgeProductService,
                         FridgeFacade fridgeFacade) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
        this.recipeFacade = recipeFacade;
        this.fridgeService = fridgeService;
        this.fridgeProductService = fridgeProductService;
    }

    public Recipe createRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = recipeFacade.getEntity(recipeDTO);
        return createRecipe(recipe);
    }

    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Set<Recipe> getRecipeListByProducts(List<Product> products) throws Exception {
        List<Ingredient> ingredientsByProducts = ingredientService.getIngredientListByProductsList(products);
        return ingredientsByProducts.stream().map(Ingredient::getRecipe).collect(Collectors.toSet());
    }

    public Map<Long, List<Product>> getProductsInFridgeSetByRecipeIdMap(List<Recipe> recipes, List<Product> products){
        Map<Long, List<Product>> result = new HashMap<>();
        recipes.forEach(_recipe -> {
            Long recipeId = _recipe.getRecipeId();
            List<Product> recipeProducts = new ArrayList<>();
            _recipe
                    .getIngredients()
                    .stream()
                    .map(Ingredient::getProduct)
                    .filter(product -> products.contains(product) && !recipeProducts.contains(product))
                    .forEachOrdered(recipeProducts::add);
            if (!ObjectUtils.isEmpty(recipeId) && !CollectionUtils.isEmpty(recipeProducts)) {
                result.put(recipeId, recipeProducts);
            }
        });
        return result;
    }

    public List<Recipe> findLimitRecipes(Pageable page){
        if (ObjectUtils.isEmpty(page)) {
            return new ArrayList<>();
        }
        return recipeRepository.findByOrderByRecipeId(page);
    }

}
