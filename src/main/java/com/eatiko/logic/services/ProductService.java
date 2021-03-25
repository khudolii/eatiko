package com.eatiko.logic.services;

import com.eatiko.logic.model.Product;
import com.eatiko.logic.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllByOrderByProductId();
    }

    public List<Product> getProductsByIds (Set<Long> ids) {
        return productRepository.findProductByProductIdIn(ids);
    }

    public Product getProductById(Long productId){
        return productRepository.findProductByProductIdIs(productId);
    }

}
