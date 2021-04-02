package com.eatiko.logic.web;

import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.facade.RecipeFacade;
import com.eatiko.logic.model.Recipe;
import com.eatiko.logic.processors.RecipeProcessor;
import com.eatiko.logic.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@CrossOrigin
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeFacade recipeFacade;

    @Autowired
    public RecipeController(RecipeService recipeService, RecipeFacade recipeFacade) {
        this.recipeService = recipeService;
        this.recipeFacade = recipeFacade;
    }

   @GetMapping("/getRecipeForMainPage")
    private ResponseEntity<List<RecipeDTO>> getRecipeForMainPage(){
        List<Recipe> recipes = recipeService.findAllRecipes();
        List<RecipeDTO> recipeDTOList = recipeFacade.getDTOsList(recipes);
        return new ResponseEntity<>(recipeDTOList, HttpStatus.OK);
    }


}
