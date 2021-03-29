package com.eatiko.logic.facade;

import com.eatiko.logic.dto.ACLUserDTO;
import com.eatiko.logic.model.ACLUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ACLUserFacade implements EntityConvertor<ACLUser, ACLUserDTO> {

    @Override
    public ACLUser getEntity(ACLUserDTO aclUserDTO) {
        if (aclUserDTO == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(aclUserDTO, ACLUser.class);
    }

    @Override
    public ACLUserDTO getDTO(ACLUser aclUser) {
        if (aclUser == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(aclUser, ACLUserDTO.class);
    }

    @Override
    public List<ACLUser> getEntitiesList(List<ACLUserDTO> dtosList) {
        if (dtosList == null || dtosList.isEmpty()) {
            return new ArrayList<>();
        }
        return dtosList
                .stream()
                .map(this::getEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());    }

    @Override
    public List<ACLUserDTO> getDTOsList(List<ACLUser> entitiesList) {
        if (entitiesList == null || entitiesList.isEmpty()) {
            return new ArrayList<>();
        }
        return entitiesList
                .stream()
                .map(this::getDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());    }
}
