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
@Table(name = "product_type", schema = "public")
public class ProductType {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "product_type_id")
    private Long productTypeId;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "productType")
    private List<Product> products;

    @Column (name = "type_name", nullable = false, unique = true)
    private String typeName;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(name = "create_date",updatable = false)
    private LocalDateTime createDate;

    @PrePersist
    private void onCreate(){
        this.createDate = LocalDateTime.now();
    }
}
