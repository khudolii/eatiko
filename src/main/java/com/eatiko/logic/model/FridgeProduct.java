package com.eatiko.logic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class FridgeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "fridge_id")
    private Fridge fridge;

    @OneToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "product_id")
    private Product product;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @Column (nullable = false)
    private Boolean status;

    @Column
    private Double weight;

    @PrePersist
    private void onCreate() {
        this.createDate = LocalDateTime.now();
    }

}
