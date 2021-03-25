package com.eatiko.logic.repository;

import com.eatiko.logic.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductByProductIdIs(Long productId);

    List<Product> findAllByOrderByProductId();

    List<Product> findProductByProductIdIn(Set<Long> productIds);
}
