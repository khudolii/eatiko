package com.eatiko.logic.services;

import com.eatiko.logic.dto.IngredientDTO;
import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.facade.IngredientFacade;
import com.eatiko.logic.model.Ingredient;
import com.eatiko.logic.model.Recipe;
import com.eatiko.logic.repository.IngredientRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
private IngredientFacade ingredientFacade;
private IngredientRepository ingredientRepository;

    public IngredientService(IngredientFacade ingredientFacade, IngredientRepository ingredientRepository) {
        this.ingredientFacade = ingredientFacade;
        this.ingredientRepository = ingredientRepository;
    }
    public Ingredient createIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = ingredientFacade.getEntity(ingredientDTO);
        System.out.println(ingredient.toString());
        return createIngredient(ingredient);
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }
}
