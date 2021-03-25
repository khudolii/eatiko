package com.eatiko.logic.web;

import com.eatiko.logic.dto.FridgeDTO;
import com.eatiko.logic.dto.FridgeProductDTO;
import com.eatiko.logic.facade.FridgeFacade;
import com.eatiko.logic.model.Fridge;
import com.eatiko.logic.services.FridgeProductService;
import com.eatiko.logic.services.FridgeService;
import com.eatiko.logic.validations.ResponseServiceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/fridge")
@CrossOrigin
public class FridgeController {

    private final FridgeFacade fridgeFacade;
    private final FridgeService fridgeService;
    private final ResponseServiceValidation responseServiceValidation;
    private final FridgeProductService fridgeProductService;

    @Autowired
    public FridgeController(FridgeFacade fridgeFacade, FridgeService fridgeService, ResponseServiceValidation responseServiceValidation, FridgeProductService fridgeProductService) {
        this.fridgeFacade = fridgeFacade;
        this.fridgeService = fridgeService;
        this.responseServiceValidation = responseServiceValidation;
        this.fridgeProductService = fridgeProductService;
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
    public ResponseEntity <Object> addProductToFridge (@Valid @RequestBody FridgeProductDTO fridgeProductDTO,
                                                       BindingResult bindingResult, @PathVariable("fridgeId") String fridgeId) {
        System.out.println(fridgeId);
        try {
            ResponseEntity<Object> errors = responseServiceValidation.mapValidationService(bindingResult);
            if (!ObjectUtils.isEmpty(errors)) {
                return errors;
            }
            if (ObjectUtils.isEmpty(fridgeId)){
                throw new Exception("Not found fridge id");
            }
            fridgeProductDTO.setFridgeId(Long.parseLong(fridgeId));
            fridgeProductService.addProductToFridge(fridgeProductDTO);
            return new ResponseEntity<>("Product added!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Product didn't add!", HttpStatus.BAD_REQUEST);
        }
    }
}
