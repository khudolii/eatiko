package com.eatiko.logic.repository;

import com.eatiko.logic.model.ACLUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ACLUserRepository extends JpaRepository<ACLUser, Long> {
    ACLUser findACLUserByEmail(String email);
    ACLUser findACLUserByUserIdIs(Long userId);
    ACLUser findACLUserByUserName (String userName);
}
