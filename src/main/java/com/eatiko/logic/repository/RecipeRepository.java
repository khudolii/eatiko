package com.eatiko.logic.repository;

import com.eatiko.logic.model.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
   List<Recipe> findAllRecipes();
   List<Recipe> findAllRecipes(Pageable page);


}
