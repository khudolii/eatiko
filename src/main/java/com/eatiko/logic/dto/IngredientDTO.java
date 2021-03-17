package com.eatiko.logic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class IngredientDTO {
    @JsonProperty("text")
    private String name;
    @JsonProperty("weight")
    private Double weight;
    @JsonProperty("image")
    private String imageURL;

}
