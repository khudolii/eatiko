package com.eatiko.logic.services;

import com.eatiko.logic.model.Product;
import com.eatiko.logic.repository.ProductRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Properties;
import java.util.Set;

@Service
public class ProductService {
    private static final Class<ProductService> CLAZZ = ProductService.class;
    private static final Logger logger = Logger.getLogger(CLAZZ);

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAllByOrderByProductId();
        products.removeIf(_p->_p.getProductId().equals(0L));
        return products;
    }

    public List<Product> getProductsByIds (Set<Long> ids) {
        return productRepository.findProductByProductIdIn(ids);
    }

    public Product getProductById(Long productId){
        return productRepository.findProductByProductIdIs(productId);
    }

    public Product updateProduct(Product product) {
        if(!ObjectUtils.isEmpty(product)){
            return productRepository.save(product);
        } else {
            return null;
        }
    }
}
