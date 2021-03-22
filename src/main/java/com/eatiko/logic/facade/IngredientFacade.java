package com.eatiko.logic.facade;

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
    }

    @Override
    public IngredientDTO getDTO(Ingredient ingredient) {
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
