package com.eatiko.logic.dto.factories;


import org.json.JSONArray;

public interface EntityFactory<ENTITY, DTO>{
    ENTITY getEntity(DTO dto);
    DTO getDTO(ENTITY entity);
    DTO createDTO(JSONArray json);
}
