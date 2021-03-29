package com.eatiko.logic.services;

import com.eatiko.logic.dto.IngredientDTO;
import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.facade.IngredientFacade;
import com.eatiko.logic.model.FridgeProduct;
import com.eatiko.logic.model.Ingredient;
import com.eatiko.logic.model.Product;
import com.eatiko.logic.model.Recipe;
import com.eatiko.logic.repository.IngredientRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    private static final Class<IngredientService> CLAZZ = IngredientService.class;
    private static final Logger logger = Logger.getLogger(CLAZZ);

    private IngredientFacade ingredientFacade;
    private IngredientRepository ingredientRepository;

    public IngredientService(IngredientFacade ingredientFacade, IngredientRepository ingredientRepository) {
        this.ingredientFacade = ingredientFacade;
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient createIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = ingredientFacade.getEntity(ingredientDTO);
        System.out.println(ingredient.toString());
        return createIngredient(ingredient);
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> getIngredientListByProductsList(List<Product> products) throws Exception{
        if (CollectionUtils.isEmpty(products)) {
            logger.warn("Products list is empty");
            return new ArrayList<>();
        }
        try {
            List<Ingredient> ingredients = new ArrayList<>();
            long start = System.currentTimeMillis();
            products.stream()
                    .map(_product -> ingredientRepository.findIngredientByProductIs(_product))
                    .forEach(ingredients::addAll);
            System.out.println("TEST: " + (System.currentTimeMillis() - start));
            return ingredients;
            //return ingredientRepository.findIngredientByProductIsIn(products);
        } catch (Exception e) {
            logger.error("getIngredientListByProductsList: " + e);
            throw new Exception(e);
        }
    }

    public List<Ingredient> getIngredientListByFridgeProducts (List<FridgeProduct> fridgeProducts) throws Exception {
        if (CollectionUtils.isEmpty(fridgeProducts)) {
            logger.warn("Fridge products list is empty");
            return new ArrayList<>();
        }
        List<Product> products = fridgeProducts.stream().map(FridgeProduct::getProduct).collect(Collectors.toList());
        return getIngredientListByProductsList(products);
    }
}
