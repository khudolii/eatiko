package com.eatiko.logic.services;

import com.eatiko.logic.dto.FridgeDTO;
import com.eatiko.logic.excepsion.EKFridgeNotFoundException;
import com.eatiko.logic.facade.FridgeFacade;
import com.eatiko.logic.model.ACLUser;
import com.eatiko.logic.model.Fridge;
import com.eatiko.logic.repository.FridgeRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.List;

@Service
public class FridgeService {
    private static final Class<FridgeService> CLAZZ = FridgeService.class;
    private static final Logger logger = Logger.getLogger(CLAZZ);

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
                throw new EKFridgeNotFoundException("Not Found fridges for user");
            }
            return fridges;
        } catch (Exception e) {
            throw e;
        }
    }

    public Fridge findFridgeByFridgeIdIs(Long fridgeId) throws Exception {
        try {
            Fridge fridge = fridgeRepository.findFridgeByFridgeIdIs(fridgeId);
            if (ObjectUtils.isEmpty(fridge)) {
                throw new EKFridgeNotFoundException("Fridge not found! Fridge ID =" + fridgeId);
            }
            return fridge;
        } catch (Exception e) {
            logger.error("findFridgeByFridgeIdIs: " + e.getMessage());
            throw e;
        }
    }
}

