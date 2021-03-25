package com.eatiko.logic.repository;

import com.eatiko.logic.model.ACLUser;
import com.eatiko.logic.model.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Long> {
    List<Fridge> findAllByAclUser(ACLUser aclUser);
    Fridge findFridgeByFridgeNameAndAclUser(String fridgeName, ACLUser aclUser);
    Fridge findFridgeByFridgeIdIs(Long fridgeId);
}
