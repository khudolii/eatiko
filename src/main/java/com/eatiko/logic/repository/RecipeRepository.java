package com.eatiko.logic.repository;

import com.eatiko.logic.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    /*Recipe findByRecipeName(String recipeName);
    List<Recipe> getAllRecipes();*/
}
