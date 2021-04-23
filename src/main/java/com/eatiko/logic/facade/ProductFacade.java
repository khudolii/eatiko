package com.eatiko.logic.facade;

import com.eatiko.logic.dto.ProductDTO;
import com.eatiko.logic.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ProductFacade implements EntityConvertor <Product, ProductDTO> {
    @Override
    public Product getEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(productDTO, Product.class);
    }

    @Override
    public ProductDTO getDTO(Product product) {
        if (product == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        ProductDTO productDTO =  modelMapper.map(product, ProductDTO.class);
        productDTO.setType(product.getProductType().getTypeName());
        return productDTO;
    }

    @Override
    public List<Product> getEntitiesList(List<ProductDTO> dtosList) {
        if (dtosList == null || dtosList.isEmpty()) {
            return new ArrayList<>();
        }
        return dtosList
                .stream()
                .map(this::getEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());    }

    @Override
    public List<ProductDTO> getDTOsList(List<Product> entitiesList) {
        if (entitiesList == null || entitiesList.isEmpty()) {
            return new ArrayList<>();
        }
        return entitiesList
                .stream()
                .map(this::getDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());    }
}
