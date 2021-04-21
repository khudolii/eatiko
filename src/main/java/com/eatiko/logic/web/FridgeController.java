package com.eatiko.logic.web;

import com.eatiko.logic.dto.FridgeDTO;
import com.eatiko.logic.dto.FridgeProductDTO;
import com.eatiko.logic.dto.ProductDTO;
import com.eatiko.logic.dto.RecipeDTO;
import com.eatiko.logic.facade.FridgeFacade;
import com.eatiko.logic.facade.ProductFacade;
import com.eatiko.logic.facade.RecipeFacade;
import com.eatiko.logic.model.*;
import com.eatiko.logic.services.FridgeProductService;
import com.eatiko.logic.services.FridgeService;
import com.eatiko.logic.services.RecipeService;
import com.eatiko.logic.validations.ResponseServiceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fridge")
@CrossOrigin
public class FridgeController {

    private final FridgeFacade fridgeFacade;
    private final FridgeService fridgeService;
    private final ResponseServiceValidation responseServiceValidation;
    private final FridgeProductService fridgeProductService;
    private final RecipeService recipeService;
    private final RecipeFacade recipeFacade;
    private final ProductFacade productFacade;

    @Autowired
    public FridgeController(FridgeFacade fridgeFacade, FridgeService fridgeService,
                            ResponseServiceValidation responseServiceValidation, FridgeProductService fridgeProductService,
                            RecipeService recipeService, RecipeFacade recipeFacade, ProductFacade productFacade) {
        this.fridgeFacade = fridgeFacade;
        this.fridgeService = fridgeService;
        this.responseServiceValidation = responseServiceValidation;
        this.fridgeProductService = fridgeProductService;
        this.recipeService = recipeService;
        this.recipeFacade = recipeFacade;
        this.productFacade = productFacade;
    }

    @PostMapping("/addFridge")
    public ResponseEntity<Object> createFridge(@Valid @RequestBody FridgeDTO fridgeDTO, BindingResult bindingResult, Principal principal) {
        System.out.println(principal.getName());
        try {
            ResponseEntity<Object> errors = responseServiceValidation.mapValidationService(bindingResult);
            if (!ObjectUtils.isEmpty(errors)) {
                return errors;
            }
            fridgeService.createFridge(fridgeDTO, principal);
            return new ResponseEntity<>("Fridge created!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Fridge didn't create!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getFridges")
    public ResponseEntity<List<FridgeDTO>> findAllFridgeByAclUser(Principal principal) {
        try {
            List<Fridge> fridges = fridgeService.getFridgesForUser(principal);
            List<FridgeDTO> fridgeDTOS = fridgeFacade.getDTOsList(fridges);
            return new ResponseEntity<>(fridgeDTOS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{fridgeId}/addProduct")
    public ResponseEntity<Object> addProductToFridge(@Valid @RequestBody FridgeProductDTO fridgeProductDTO,
                                                     BindingResult bindingResult, @PathVariable("fridgeId") String fridgeId) {
        try {
            ResponseEntity<Object> errors = responseServiceValidation.mapValidationService(bindingResult);
            if (!ObjectUtils.isEmpty(errors)) {
                return errors;
            }
            if (ObjectUtils.isEmpty(fridgeId)) {
                throw new Exception("Not found fridge id");
            }
            fridgeProductDTO.setFridgeId(Long.parseLong(fridgeId));
            fridgeProductService.addProductToFridge(fridgeProductDTO);
            return new ResponseEntity<>("Product added!", HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>("Product didn't add!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{fridgeId}/getRecipesByFridgeProducts")
    public ResponseEntity<FridgeDTO> getRecipesByFridgeProducts(@PathVariable("fridgeId") String fridgeId) {
        try {
            Long _fId = Long.valueOf(fridgeId);
            List<FridgeProduct> productsInFridge = fridgeProductService.findFridgeProductByFridgeId(_fId);
            List<Product> products = productsInFridge.stream().map(FridgeProduct::getProduct).collect(Collectors.toList());
            Set<Recipe> recipesByProducts = recipeService.getRecipeListByProducts(products);
            List<Recipe> recipes = new ArrayList<>(recipesByProducts);
            Map<Long, List<Product>> productsInFridgeByRecipeMap = recipeService.getProductsInFridgeSetByRecipeIdMap(recipes, products);
            List<RecipeDTO> recipeDTOList = new ArrayList<>();
            recipes
                    .stream()
                    .filter(_recipe -> productsInFridgeByRecipeMap.containsKey(_recipe.getRecipeId()))
                    .forEach(_recipe -> {
                        Long recipeId = _recipe.getRecipeId();
                        List<ProductDTO> productDTOS = productFacade.getDTOsList(productsInFridgeByRecipeMap.get(recipeId));
                        RecipeDTO recipeDTO = new RecipeDTO();
                        recipeDTO.setRecipeId(recipeId);
                        recipeDTO.setName(_recipe.getRecipeName());
                        recipeDTO.setImageURL(_recipe.getImageUrl());
                        recipeDTO.setProductsInFridge(productDTOS);
                        recipeDTOList.add(recipeDTO);
                    });
            FridgeDTO fridgeDTO = new FridgeDTO();
            fridgeDTO.setFridgeId(Long.valueOf(fridgeId));
            fridgeDTO.setRecipeDTOList(recipeDTOList);
            return new ResponseEntity<>(fridgeDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{fridgeId}/getFridgeById")
    public ResponseEntity<FridgeDTO> getFridgeById(@PathVariable("fridgeId") String fridgeId) {
        try {
            Long _fid = Long.parseLong(fridgeId);
            Fridge fridge = fridgeService.findFridgeByFridgeIdIs(_fid);
            FridgeDTO fridgeDTO = fridgeFacade.getDTO(fridge);
            return new ResponseEntity<>(fridgeDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
