package com.eatiko.logic.api;

import org.json.JSONObject;

import java.util.List;

public interface BaseAPI <ENTITY, DTO> {
    String getApiURL();
    String getApiRequestWithParameter (ENTITY entity);
    String getApiRequestWithParameters (List<ENTITY> entities);
    List<DTO> createDTOListByJSON(JSONObject json);


}
