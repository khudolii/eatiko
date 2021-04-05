package com.eatiko.logic.web;

import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.facade.RecipeFacade;
import com.eatiko.logic.model.Recipe;
import com.eatiko.logic.processors.RecipeProcessor;
import com.eatiko.logic.services.RecipeService;
import com.eatiko.logic.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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
    private ResponseEntity<List<RecipeDTO>> getRecipeForMainPage() {
        ResponseEntity<List<RecipeDTO>> result;
        try {
            Long numOfRecipes = recipeService.countAllRecipes();
            if (ObjectUtils.isEmpty(numOfRecipes) || numOfRecipes.equals(0L)){
                numOfRecipes = 1L;
            } else {
                numOfRecipes/=10;
            }
            int num = (int) (Math.random()*numOfRecipes);
            Pageable page = PageRequest.of(num, AppConstants.NUM_OF_RECIPES_ON_INDEX_PAGE);
            List<Recipe> recipes = recipeService.findLimitRecipes(page);
            List<RecipeDTO> recipeDTOList = recipeFacade.getDTOsList(recipes);
            result = new ResponseEntity<>(recipeDTOList, HttpStatus.OK);
        } catch (Exception e) {
            result = new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
        }

        return result;
    }


}
