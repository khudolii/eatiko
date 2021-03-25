package com.eatiko.logic.repository;

import com.eatiko.logic.model.Fridge;
import com.eatiko.logic.model.FridgeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FridgeProductRepository extends JpaRepository<FridgeProduct, Long> {
    List<FridgeProduct> findFridgeProductByFridge(Fridge fridge);
}
