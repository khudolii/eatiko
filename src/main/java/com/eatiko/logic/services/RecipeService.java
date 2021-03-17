package com.eatiko.logic.services;

import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.facade.RecipeFacade;
import com.eatiko.logic.model.Recipe;
import com.eatiko.logic.repository.IngredientRepository;
import com.eatiko.logic.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;
    private IngredientRepository ingredientRepository;
    private RecipeFacade recipeFacade;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, RecipeFacade recipeFacade) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeFacade = recipeFacade;
    }

    public Recipe createRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = recipeFacade.getEntity(recipeDTO);
        System.out.println(recipe.toString());
        return recipeRepository.save(recipe);
    }
}
