package com.eatiko.logic.model;

import com.eatiko.logic.model.enums.EShelfLife;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "product", schema = "public")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

   @ManyToOne(fetch = FetchType.EAGER)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    private EShelfLife shelfLifeType;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_ingredient",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients = new ArrayList<>();

  /*  public Product(String name) {
        this.name = name;
    }*/

    @PrePersist
    private void onCreate() {
        this.createDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", productType=" + productType +
                ", shelfLifeType=" + shelfLifeType +
                ", createDate=" + createDate +
                ", ingredients=" + ingredients +
                '}';
    }
}
