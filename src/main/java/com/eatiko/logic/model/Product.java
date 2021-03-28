package com.eatiko.logic.model;

import com.eatiko.logic.model.enums.EShelfLife;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
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

    @PrePersist
    private void onCreate() {
        this.createDate = LocalDateTime.now();
    }

}
