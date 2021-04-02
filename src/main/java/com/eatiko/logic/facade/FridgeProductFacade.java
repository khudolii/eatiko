package com.eatiko.logic.facade;

import com.eatiko.logic.dto.FridgeProductDTO;
import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.model.ACLUser;
import com.eatiko.logic.model.FridgeProduct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FridgeProductFacade implements EntityConvertor<FridgeProduct, FridgeProductDTO> {
    @Override
    public FridgeProduct getEntity(FridgeProductDTO fridgeProductDTO) {
        if (fridgeProductDTO == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(fridgeProductDTO, FridgeProduct.class);
    }

    @Override
    public FridgeProductDTO getDTO(FridgeProduct fridgeProduct) {
        if (fridgeProduct == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(fridgeProduct, FridgeProductDTO.class);
    }

    @Override
    public List<FridgeProduct> getEntitiesList(List<FridgeProductDTO> dtosList) {
        if (dtosList == null || dtosList.isEmpty()) {
            return new ArrayList<>();
        }
        return dtosList
                .stream()
                .map(this::getEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());    }

    @Override
    public List<FridgeProductDTO> getDTOsList(List<FridgeProduct> entitiesList) {
        if (entitiesList == null || entitiesList.isEmpty()) {
            return new ArrayList<>();
        }
        return entitiesList
                .stream()
                .map(this::getDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());    }
}
