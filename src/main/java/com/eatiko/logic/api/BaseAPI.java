package com.eatiko.logic.api;

import org.json.JSONObject;

import java.util.List;

public interface BaseAPI <DTO> {
    String getApiURL();
    String getApiRequestWithParameter (String parameter);
    String getApiRequestWithParameters (List<String> parameters);
    List<DTO> createDTOListByJSON(JSONObject json);


}
