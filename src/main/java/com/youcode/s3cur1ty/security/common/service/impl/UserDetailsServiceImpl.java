package com.youcode.s3cur1ty.security.common.service.impl;

import com.youcode.s3cur1ty.app.core.service.UserService;
import com.youcode.s3cur1ty.security.common.principal.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final Principal principal;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        principal.setUser(userService.findByID(username));
        return principal;
    }
}