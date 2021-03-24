package com.eatiko.logic.services;

import com.eatiko.logic.model.ACLUser;
import com.eatiko.logic.payload.request.SignupRequest;
import com.eatiko.logic.repository.ACLUserRepository;
import com.eatiko.logic.security.JWTTokenProvider;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ACLUserService {
    private static final Class<ACLUserService> CLAZZ = ACLUserService.class;
    private static final Logger logger = Logger.getLogger(CLAZZ);

    private final ACLUserRepository aclUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ACLUserService(ACLUserRepository aclUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.aclUserRepository = aclUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ACLUser addUser(SignupRequest signupRequest) {
        ACLUser user = new ACLUser();

        re
    }
}
