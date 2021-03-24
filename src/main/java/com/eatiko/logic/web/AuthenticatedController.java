package com.eatiko.logic.web;

import com.eatiko.logic.dto.ACLUserDTO;
import com.eatiko.logic.excepsion.EKCreateUserException;
import com.eatiko.logic.payload.request.LoginRequest;
import com.eatiko.logic.payload.response.JWTTokenSuccessResponse;
import com.eatiko.logic.security.JWTTokenProvider;
import com.eatiko.logic.services.ACLUserService;
import com.eatiko.logic.utils.AppConstants;
import com.eatiko.logic.validations.ResponseServiceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@PreAuthorize("permitAll()")

public class AuthenticatedController {
    private JWTTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private ResponseServiceValidation responseServiceValidation;
    private ACLUserService aclUserService;

    @Autowired
    public AuthenticatedController(JWTTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager,
                                   ResponseServiceValidation responseServiceValidation, ACLUserService aclUserService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.responseServiceValidation = responseServiceValidation;
        this.aclUserService = aclUserService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> registerNewUser(@Valid @RequestBody ACLUserDTO aclUserDTO, BindingResult bindingResult){
        ResponseEntity<Object> errors = responseServiceValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        try {
            aclUserService.addUser(aclUserDTO);
            return ResponseEntity.ok("User registered successfully");
        } catch (EKCreateUserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticationOfUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseServiceValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = AppConstants.SECURITY_TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
    }
}
