package com.eatiko.logic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
        {
                "label",
                "image",
                "source",
                "url",
                "calories",
                "totalWeight",
                "totalTime",
                "cuisineType",
                "dishType",
                "ingredients"
        }
)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Component
public class RecipeDTO {
    @JsonProperty ("label")
    private String label;
    @JsonProperty
    private String image;
    @JsonProperty
    private String source;

}
