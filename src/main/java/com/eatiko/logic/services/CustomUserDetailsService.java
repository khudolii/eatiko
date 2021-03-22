package com.eatiko.logic.services;

import com.eatiko.logic.model.ACLUser;
import com.eatiko.logic.repository.ACLUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ACLUserRepository aclUserRepository;

    @Autowired
    public CustomUserDetailsService(ACLUserRepository aclUserRepository) {
        this.aclUserRepository = aclUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        ACLUser user = aclUserRepository.findACLUserByEmail(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User " + userName + " not found!");
        }
        return build(user);
    }

    public ACLUser getACLUserById(Long userId) {
        return aclUserRepository.findACLUserByUserIdIs(userId);
    }

    public static ACLUser build(ACLUser user) {
        List<GrantedAuthority> authorities = user
                .getUserRoles()
                .stream()
                .map(_role -> new SimpleGrantedAuthority(_role.name()))
                .collect(Collectors.toList());

        return new ACLUser(user.getUserId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
    }
}
