package com.eatiko.logic.facade;

import com.eatiko.logic.dto.FridgeProductDTO;
import com.eatiko.logic.model.ACLUser;
import com.eatiko.logic.model.FridgeProduct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FridgeProductFacade implements EntityConvertor<FridgeProduct, FridgeProductDTO> {
    @Override
    public FridgeProduct getEntity(FridgeProductDTO fridgeProductDTO) {
        if (fridgeProductDTO == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(fridgeProductDTO, FridgeProduct.class);
    }

    @Override
    public FridgeProductDTO getDTO(FridgeProduct fridgeProduct) {
        return null;
    }

    @Override
    public List<FridgeProduct> getEntitiesList(List<FridgeProductDTO> dtosList) {
        return null;
    }

    @Override
    public List<FridgeProductDTO> getDTOsList(List<FridgeProduct> entitiesList) {
        return null;
    }
}
