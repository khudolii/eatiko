package com.eatiko.logic.facade;

import com.eatiko.logic.dto.ACLUserDTO;
import com.eatiko.logic.dto.FridgeDTO;
import com.eatiko.logic.dto.FridgeProductDTO;
import com.eatiko.logic.dto.IngredientDTO;
import com.eatiko.logic.model.ACLUser;
import com.eatiko.logic.model.Fridge;
import com.eatiko.logic.model.FridgeProduct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FridgeFacade implements EntityConvertor<Fridge, FridgeDTO> {
    private FridgeProductFacade fridgeProductFacade;
    private ACLUserFacade aclUserFacade;

    @Autowired
    public FridgeFacade(FridgeProductFacade fridgeProductFacade, ACLUserFacade aclUserFacade) {
        this.fridgeProductFacade = fridgeProductFacade;
        this.aclUserFacade = aclUserFacade;
    }

    @Override
    public Fridge getEntity(FridgeDTO fridgeDTO) {
        if (fridgeDTO == null) {
            return null;
        }
        Fridge fridge = new Fridge();
        fridge.setFridgeName(fridgeDTO.getFridgeName());
        if (!ObjectUtils.isEmpty(fridgeDTO.getAclUserDTO())) {
            ACLUser aclUser = aclUserFacade.getEntity(fridgeDTO.getAclUserDTO());
            fridge.setAclUser(aclUser);
        }
        if (!CollectionUtils.isEmpty(fridgeDTO.getFridgeProducts())) {
            List<FridgeProduct> fridgeProducts = fridgeProductFacade.getEntitiesList(fridgeDTO.getFridgeProducts());
            fridge.setFridgeProducts(fridgeProducts);
        }
        return fridge;
    }

    @Override
    public FridgeDTO getDTO(Fridge fridge) {
        if (fridge == null) {
            return null;
        }
        FridgeDTO fridgeDTO = new FridgeDTO();
        fridgeDTO.setFridgeId(fridge.getFridgeId());
        fridgeDTO.setFridgeName(fridge.getFridgeName());
        if (!ObjectUtils.isEmpty(fridge.getAclUser())) {
            ACLUserDTO aclUser = aclUserFacade.getDTO(fridge.getAclUser());
            fridgeDTO.setAclUserDTO(aclUser);
        }
        if (!CollectionUtils.isEmpty(fridge.getFridgeProducts())) {
            List<FridgeProductDTO> fridgeProducts = fridgeProductFacade.getDTOsList(fridge.getFridgeProducts());
            fridgeDTO.setFridgeProducts(fridgeProducts);
        }
        return fridgeDTO;
    }

    @Override
    public List<Fridge> getEntitiesList(List<FridgeDTO> dtosList) {
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
