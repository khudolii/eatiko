package com.eatiko.logic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class CuisineType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cousineTypeId;

    @Column (nullable = false, unique = true)
    private String cousineTypeName;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "cuisineType")
    private List<Recipe> recipes = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(name = "create_date",updatable = false)
    private LocalDateTime createDate;

    @PrePersist
    private void onCreate(){
        this.createDate = LocalDateTime.now();
    }

}
