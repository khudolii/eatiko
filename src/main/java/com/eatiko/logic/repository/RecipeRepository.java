package com.eatiko.logic.repository;

import com.eatiko.logic.model.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
   //List<Recipe> findAllRecipes();
   List<Recipe> findByOrderByRecipeId(Pageable page);
   Long countAllBy();
   @Query(value = "select recipe_name from recipe", nativeQuery = true)
   Set<String> findRecipesNames();


}
