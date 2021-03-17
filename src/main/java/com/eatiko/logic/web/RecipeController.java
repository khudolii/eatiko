package com.eatiko.logic.web;

import com.eatiko.logic.processors.RecipeProcessor;
import com.eatiko.logic.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {
    @Autowired
    private RecipeProcessor recipeProcessor;

    @GetMapping("/test")
    public ResponseEntity<String> getRecipe(){
        recipeProcessor.startParsingRecipes();
        return ResponseEntity.ok("Ok");
    }
}
