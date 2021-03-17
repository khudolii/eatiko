package com.eatiko.logic.facade;


import org.apache.catalina.core.ApplicationContext;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface EntityConvertor<ENTITY, DTO>{
    ENTITY getEntity(DTO dto);
    DTO getDTO(ENTITY entity);
    List<ENTITY> getEntitiesList(List<DTO> dtosList);
    List<DTO> getDTOsList(List<ENTITY> entitiesList);
}
