package com.eatiko.logic.facade;

import com.eatiko.logic.dto.ACLUserDTO;
import com.eatiko.logic.model.ACLUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ACLUserFacade implements EntityConvertor<ACLUser, ACLUserDTO> {

    @Override
    public ACLUser getEntity(ACLUserDTO aclUserDTO) {
        if (aclUserDTO == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(aclUserDTO, ACLUser.class);    }

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
        return null;
    }

    @Override
    public List<ACLUserDTO> getDTOsList(List<ACLUser> entitiesList) {
        return null;
    }
}
