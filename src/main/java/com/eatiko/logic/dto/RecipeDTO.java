package com.eatiko.logic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

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
                "ingredients"
        }
)

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Component
public class RecipeDTO {
    private Long recipeId;
    @JsonProperty ("label")
    private String name;
    @JsonProperty ("image")
    private String imageURL;
    @JsonProperty ("source")
    private String source;
    @JsonProperty ("url")
    private String sourceURL;
    @JsonProperty ("calories")
    private Double calories;
    @JsonProperty ("totalWeight")
    private Double totalWeight;
    @JsonProperty ("totalTime")
    private Double totalTime;
    @JsonProperty ("cuisineType")
    private List<String> cuisineType;
    @JsonProperty ("ingredients")
    private Set<IngredientDTO> ingredients;

    private List<ProductDTO> productsInFridge;

}
