package com.eatiko.logic.facade;

import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.model.Ingredient;
import com.eatiko.logic.model.Recipe;
import com.eatiko.logic.utils.AppUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RecipeFacade implements EntityConvertor<Recipe, RecipeDTO> {

    private IngredientFacade ingredientFacade;

    @Autowired
    public RecipeFacade(IngredientFacade ingredientFacade) {
        this.ingredientFacade = ingredientFacade;
    }



    @Override
    public Recipe getEntity(RecipeDTO recipeDTO) {
        if (recipeDTO == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(recipeDTO, Recipe.class);
    }

    @Override
    public RecipeDTO getDTO(Recipe recipe) {
        if (recipe == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(recipe, RecipeDTO.class);
    }

    @Override
    public List<Recipe> getEntitiesList(List<RecipeDTO> dtosList) {
        if (dtosList == null || dtosList.isEmpty()) {
            return new ArrayList<>();
        }
        return dtosList
                .stream()
                .map(this::getEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTO> getDTOsList(List<Recipe> entitiesList) {
        if (entitiesList == null || entitiesList.isEmpty()) {
            return new ArrayList<>();
        }
        return entitiesList
                .stream()
                .map(this::getDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
