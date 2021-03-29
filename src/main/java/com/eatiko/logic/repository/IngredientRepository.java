package com.eatiko.logic.repository;

import com.eatiko.logic.model.Ingredient;
import com.eatiko.logic.model.Product;
import com.eatiko.logic.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findIngredientsByRecipeIs(Recipe recipe);
    List<Ingredient> findIngredientByProductIsIn(List<Product> products);
    List<Ingredient> findIngredientByProductIs(Product product);
}
