package com.eatiko.logic.services;

import com.eatiko.logic.dto.FridgeDTO;
import com.eatiko.logic.dto.FridgeProductDTO;
import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.facade.FridgeFacade;
import com.eatiko.logic.facade.RecipeFacade;
import com.eatiko.logic.model.Fridge;
import com.eatiko.logic.model.FridgeProduct;
import com.eatiko.logic.model.Recipe;
import com.eatiko.logic.repository.IngredientRepository;
import com.eatiko.logic.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Set;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final RecipeFacade recipeFacade;
    private final FridgeService fridgeService;
    private final FridgeProductService fridgeProductService;
    private final FridgeFacade fridgeFacade;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, IngredientService ingredientService,
                         RecipeFacade recipeFacade, FridgeService fridgeService, FridgeProductService fridgeProductService,
                         FridgeFacade fridgeFacade) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
        this.recipeFacade = recipeFacade;
        this.fridgeService = fridgeService;
        this.fridgeProductService = fridgeProductService;
        this.fridgeFacade = fridgeFacade;
    }

    public Recipe createRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = recipeFacade.getEntity(recipeDTO);
        System.out.println(recipe.toString());
        return createRecipe(recipe);
    }

    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }


    public List<Recipe> getRecipesByFridgeProducts (FridgeDTO fridgeDTO, BindingResult bindingResult) {
        Fridge fridge = fridgeFacade.getEntity(fridgeDTO);
        List<FridgeProduct> fridgeProducts = fridgeProductService.findFridgeProductByFridge(fridge);
        Set<Long>
        for (FridgeProduct fridgeProduct : fridgeProducts) {
            //fridgeProduct.getProduct().get
        }
    }
}
