package com.eatiko.logic.services;

import com.eatiko.logic.dto.FridgeProductDTO;
import com.eatiko.logic.model.Fridge;
import com.eatiko.logic.model.FridgeProduct;
import com.eatiko.logic.model.Product;
import com.eatiko.logic.repository.FridgeProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FridgeProductService {
    private final FridgeService fridgeService;
    private final ProductService productService;
    private final FridgeProductRepository fridgeProductRepository;

    @Autowired
    public FridgeProductService(FridgeService fridgeService, ProductService productService, FridgeProductRepository fridgeProductRepository) {
        this.fridgeService = fridgeService;
        this.productService = productService;
        this.fridgeProductRepository = fridgeProductRepository;
    }

    public FridgeProduct addProductToFridge(FridgeProductDTO fpDTO) throws Exception {
        try {
            if (fpDTO.getProductId() == null) {
                throw new Exception("Product ID is empty");
            }
            Product product = productService.getProductById(fpDTO.getProductId());
            Fridge fridge = fridgeService.findFridgeByFridgeIdIs(fpDTO.getFridgeId());
            FridgeProduct fridgeProduct = new FridgeProduct();
            fridgeProduct.setFridge(fridge);
            fridgeProduct.setProduct(product);
            fridgeProduct.setStatus(true);
            fridgeProduct.setWeight(fpDTO.getWeight());
            return fridgeProductRepository.save(fridgeProduct);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<FridgeProduct> findFridgeProductByFridge (Fridge fridge) {
        return fridgeProductRepository.findFridgeProductByFridge(fridge);
    }
}
