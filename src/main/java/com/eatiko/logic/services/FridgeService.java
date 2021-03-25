package com.eatiko.logic.services;

import com.eatiko.logic.dto.FridgeDTO;
import com.eatiko.logic.dto.FridgeProductDTO;
import com.eatiko.logic.facade.FridgeFacade;
import com.eatiko.logic.model.ACLUser;
import com.eatiko.logic.model.Fridge;
import com.eatiko.logic.model.FridgeProduct;
import com.eatiko.logic.model.Product;
import com.eatiko.logic.repository.FridgeProductRepository;
import com.eatiko.logic.repository.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class FridgeService {
    private final FridgeRepository fridgeRepository;
    private final FridgeFacade fridgeFacade;
    private final ProductService productService;
    private final ACLUserService aclUserService;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository, FridgeFacade fridgeFacade, ProductService productService, ACLUserService aclUserService) {
        this.fridgeRepository = fridgeRepository;
        this.fridgeFacade = fridgeFacade;
        this.productService = productService;
        this.aclUserService = aclUserService;
    }

    public Fridge createFridge(FridgeDTO fridgeDTO, Principal principal) throws Exception {
        try {
            Fridge fridge = fridgeFacade.getEntity(fridgeDTO);
            if (ObjectUtils.isEmpty(fridge)) {
                throw new Exception("Fridge is null");
            }
            ACLUser user = aclUserService.getCurrentUser(principal);
            fridge.setAclUser(user);
            return fridgeRepository.save(fridge);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Fridge> getFridgesForUser(Principal principal) throws Exception {
        try {
            ACLUser user = aclUserService.getCurrentUser(principal);
            List<Fridge> fridges = fridgeRepository.findAllByAclUser(user);
            if (CollectionUtils.isEmpty(fridges)) {
                throw new Exception("Not Found fridges for user");
            }
            return fridges;
        } catch (Exception e) {
            throw e;
        }
    }

    public Fridge findFridgeByFridgeIdIs (Long fridgeId) {
        return fridgeRepository.findFridgeByFridgeIdIs(fridgeId);
    }
}

