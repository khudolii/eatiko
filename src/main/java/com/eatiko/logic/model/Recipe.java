package com.eatiko.logic.model;

import com.eatiko.logic.dto.IngredientDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @Column (nullable = false, unique = true)
    private String recipeName;

    @Column
    private String imageUrl;

    @Column
    private String source;

    @Column
    private String sourceUrl;

    @Column
    private Double calories;

    @Column
    private Double totalWeight;

    @Column
    private Double totalTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private CuisineType cuisineType;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "recipe")
    private List<Ingredient> ingredients;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(name = "create_date",updatable = false)
    private LocalDateTime createDate;

    @PrePersist
    private void onCreate(){
        this.createDate = LocalDateTime.now();
    }
}
