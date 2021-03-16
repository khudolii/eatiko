package com.eatiko.logic.dto.factories;


import org.json.JSONObject;

public interface EntityFactory<ENTITY, DTO>{
    ENTITY getEntity(DTO dto);
    DTO getDTO(ENTITY entity);
}
