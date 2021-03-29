package com.eatiko.logic.facade;

import com.eatiko.logic.dto.FridgeDTO;
import com.eatiko.logic.dto.IngredientDTO;
import com.eatiko.logic.model.Fridge;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FridgeFacade implements EntityConvertor<Fridge, FridgeDTO> {
    @Override
    public Fridge getEntity(FridgeDTO fridgeDTO) {
        if (fridgeDTO == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(fridgeDTO, Fridge.class);
    }

    @Override
    public FridgeDTO getDTO(Fridge fridge) {
        if (fridge == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(fridge, FridgeDTO.class);    }

    @Override
    public List<Fridge> getEntitiesList(List<FridgeDTO> dtosList) {
        if (dtosList == null || dtosList.isEmpty()) {
            return new ArrayList<>();
        }
        return dtosList
                .stream()
                .map(this::getEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());    }

    @Override
    public List<FridgeDTO> getDTOsList(List<Fridge> entitiesList) {
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
