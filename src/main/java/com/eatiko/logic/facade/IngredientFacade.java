package com.eatiko.logic.facade;

import com.eatiko.logic.dto.IngredientDTO;
import com.eatiko.logic.model.Ingredient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        if (dtosList == null || dtosList.isEmpty()) {
            return new ArrayList<>();
        }
        return dtosList
                .stream()
                .map(this::getEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());    }

    @Override
    public List<IngredientDTO> getDTOsList(List<Ingredient> entitiesList) {
        if (entitiesList == null || entitiesList.isEmpty()) {
            return new ArrayList<>();
        }
        return entitiesList
                .stream()
                .map(this::getDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());    }
}
