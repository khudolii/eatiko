package com.eatiko.logic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;

    @Column (nullable = false)
    private Long productId;

    @Column (nullable = false)
    private String name;

    @Column
    private Double weight;

    @Column
    private String imageURL;

    @ManyToOne
    private Recipe recipe;

    @ManyToMany(mappedBy = "ingredients")
    private List<Product> products;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(name = "create_date",updatable = false)
    private LocalDateTime createDate;

    @PrePersist
    private void onCreate(){
        this.createDate = LocalDateTime.now();
    }

}
