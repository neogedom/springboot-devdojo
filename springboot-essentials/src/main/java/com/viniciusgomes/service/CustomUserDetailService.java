package com.viniciusgomes.service;

import com.viniciusgomes.model.User;
import com.viniciusgomes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");//no PreAuthorize ele adiciona "ROLE_" antes do nome do role
        List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");//no PreAuthorize ele adiciona "ROLE_" antes do nome do role
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(),
                user.isAdmin() ? authorityListAdmin : authorityListUser);
    }
}