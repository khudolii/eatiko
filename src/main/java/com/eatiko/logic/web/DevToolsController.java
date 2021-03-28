package com.eatiko.logic.web;

import com.eatiko.logic.dto.FridgeDTO;
import com.eatiko.logic.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/test")
public class DevToolsController {

    private final RecipeService recipeService;

    @Autowired
    public DevToolsController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{fridgeId}/getRecipesByFridge")
    public void getRecipesByFridge(@PathVariable String fridgeId) {
        if (!ObjectUtils.isEmpty(fridgeId)) {
            recipeService.getRecipesByFridgeProducts(Long.valueOf(fridgeId));
        }
    }
}
