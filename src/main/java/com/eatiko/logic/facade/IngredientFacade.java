package com.eatiko.logic.facade;

import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.utils.AppUtil;
import com.eatiko.logic.dto.IngredientDTO;
import com.eatiko.logic.model.Ingredient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IngredientFacade implements EntityConvertor<Ingredient, IngredientDTO> {

    @Override
    public Ingredient getEntity(IngredientDTO ingredientDTO) {
        if (ingredientDTO == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(ingredientDTO, Ingredient.class);
        /*if (ingredientDTO == null) {
            return null;
        }
        Ingredient ingredient = AppUtil.getContext().getBean("ingredient", Ingredient.class);
        ingredient.setName(ingredientDTO.getName());
        ingredient.setImageURL(ingredientDTO.getImageURL());
        ingredient.setWeight(ingredientDTO.getWeight());
        return ingredient;*/
    }

    @Override
    public IngredientDTO getDTO(Ingredient ingredient) {
/*        if (ingredient == null) {
            return null;
        }
        IngredientDTO ingredientDTO = AppUtil.getContext().getBean("ingredientDTO", IngredientDTO.class);
        ingredientDTO.setName(ingredient.getName());
        ingredientDTO.setImageURL(ingredient.getImageURL());
        ingredientDTO.setWeight(ingredient.getWeight());
        return ingredientDTO;*/
        if (ingredient == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(ingredient, IngredientDTO.class);
    }

    @Override
    public List<Ingredient> getEntitiesList(List<IngredientDTO> dtosList) {
        return null;
    }

    @Override
    public List<IngredientDTO> getDTOsList(List<Ingredient> entitiesList) {
        return null;
    }
}
