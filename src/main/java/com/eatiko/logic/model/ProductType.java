package com.eatiko.logic.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "product_type", schema = "public")
public class ProductType {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "product_type_id")
    private Long productTypeId;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "productType")
    private Set<Product> products;

    @Column (name = "type_name", nullable = false, unique = true)
    private String typeName;
}
