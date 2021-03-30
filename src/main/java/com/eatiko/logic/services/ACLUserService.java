package com.eatiko.logic.services;

import com.eatiko.logic.dto.ACLUserDTO;
import com.eatiko.logic.excepsion.EKCreateUserException;
import com.eatiko.logic.facade.ACLUserFacade;
import com.eatiko.logic.model.ACLUser;
import com.eatiko.logic.model.enums.EUserRole;
import com.eatiko.logic.repository.ACLUserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;

@Service
public class ACLUserService {
    private static final Class<ACLUserService> CLAZZ = ACLUserService.class;
    private static final Logger logger = Logger.getLogger(CLAZZ);

    private final ACLUserFacade aclUserFacade;
    private final ACLUserRepository aclUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ACLUserService(ACLUserFacade aclUserFacade, ACLUserRepository aclUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.aclUserRepository = aclUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.aclUserFacade = aclUserFacade;
    }

    public ACLUser addUser(ACLUserDTO aclUserDTO) throws EKCreateUserException {
        try {
            ACLUser user = aclUserFacade.getEntity(aclUserDTO);
            if (user == null) {
                throw new Exception("User entity is null! UserName: " + aclUserDTO.getUsername());
            }
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.getUserRoles().add(EUserRole.USER_ROLE);

            logger.info("Save new user: " + user.toString());
            return aclUserRepository.save(user);
        } catch (Exception e) {
            logger.error("An error occurred while registering a new user: " + e);
            throw new EKCreateUserException("An error occurred while registering a new user");
        }
    }

    public ACLUser getCurrentUser(Principal principal) throws Exception {
        try {
            String userName = principal.getName();
            if (StringUtils.isEmpty(userName)) {
                throw new UserPrincipalNotFoundException("Not found user in principal!");
            }

            ACLUser user = aclUserRepository.findACLUserByUserName(userName);
            if (ObjectUtils.isEmpty(user)) {
                throw new UsernameNotFoundException("User not found with user name - " + userName);
            }
            return user;
        } catch (Exception e) {
            logger.error("getCurrentUser: " + e);
            throw new Exception(e);
        }
    }
}
