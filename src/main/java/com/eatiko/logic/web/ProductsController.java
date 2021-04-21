package com.eatiko.logic.web;

import com.eatiko.logic.dto.ProductDTO;
import com.eatiko.logic.facade.ProductFacade;
import com.eatiko.logic.model.Product;
import com.eatiko.logic.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductsController {
    private final ProductFacade productFacade;
    private final ProductService productService;

    @Autowired
    public ProductsController(ProductFacade productFacade, ProductService productService) {
        this.productFacade = productFacade;
        this.productService = productService;
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDTO>> findAllProducts(){
        try {
           List<Product> products = productService.getAllProducts();
           List<ProductDTO> productDTOS = productFacade.getDTOsList(products);
           return new ResponseEntity<>(productDTOS, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
        }
    }
}
