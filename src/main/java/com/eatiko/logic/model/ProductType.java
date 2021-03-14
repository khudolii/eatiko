package com.eatiko.logic.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class ProductType {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long productTypeId;

    @Column(nullable = false)
    @OneToMany (fetch = FetchType.EAGER, mappedBy = "productType")
    private Set<Product> products;

    @Column (nullable = false, unique = true)
    private String typeName;
}
