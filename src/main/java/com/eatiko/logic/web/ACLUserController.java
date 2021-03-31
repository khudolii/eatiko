package com.eatiko.logic.web;

import com.eatiko.logic.dto.ACLUserDTO;
import com.eatiko.logic.facade.ACLUserFacade;
import com.eatiko.logic.model.ACLUser;
import com.eatiko.logic.services.ACLUserService;
import com.eatiko.logic.validations.ResponseServiceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class ACLUserController {

    private ACLUserService userService;
    private ACLUserFacade userFacade;
    private ResponseServiceValidation responseServiceValidation;

    @Autowired
    public ACLUserController(ACLUserService userService, ACLUserFacade userFacade, ResponseServiceValidation responseServiceValidation) {
        this.userService = userService;
        this.userFacade = userFacade;
        this.responseServiceValidation = responseServiceValidation;
    }

    @GetMapping("/")
    public ResponseEntity<ACLUserDTO> getCurrentUser(Principal principal) {
        try {
            ACLUser user = userService.getCurrentUser(principal);
            ACLUserDTO userDTO = userFacade.getDTO(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
