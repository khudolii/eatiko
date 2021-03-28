package com.eatiko.logic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
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
