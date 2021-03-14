package com.eatiko.logic.model;

import com.eatiko.logic.model.enums.EProductTypes;
import com.eatiko.logic.model.enums.EShelfLife;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductType productType;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createDate;

    @PrePersist
    private void onCreate(){
        this.createDate = LocalDateTime.now();
    }
}
