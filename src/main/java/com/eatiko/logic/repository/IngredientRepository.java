package com.eatiko.logic.repository;

import com.eatiko.logic.model.Ingredient;
import com.eatiko.logic.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
   // List<Ingredient> findIngredientsByRecipeIsIn(Recipe recipe);
}
