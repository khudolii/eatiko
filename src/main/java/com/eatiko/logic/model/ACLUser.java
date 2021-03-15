package com.eatiko.logic.model;

import com.eatiko.logic.model.enums.EUserRole;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class ACLUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_Id")
    private Long userId;

    @Column (name = "user_name",nullable = false, unique = true)
    private String userName;

    @Column(name = "first_name", nullable = false, unique = true)
    private String firstName;

    @Column(name = "last_name", nullable = false, unique = true)
    private String lastName;

    @Column (name = "email", unique = true)
    private String email;

    @Column (name = "password", nullable = false)
    private String password;

    @ElementCollection(targetClass = EUserRole.class)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "userId"))
    private Set<EUserRole> userRoles = new HashSet<>();

    @OneToMany (fetch = FetchType.LAZY)
    @JoinColumn (name = "product_id")
    private Set<Product> products;


}
