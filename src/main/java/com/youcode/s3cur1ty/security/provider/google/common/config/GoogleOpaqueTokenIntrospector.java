package com.youcode.s3cur1ty.security.provider.google.common.config;

import com.youcode.s3cur1ty.app.core.database.model.entity.Role;
import com.youcode.s3cur1ty.app.core.database.model.entity.User;
import com.youcode.s3cur1ty.app.core.database.repository.RoleRepository;
import com.youcode.s3cur1ty.app.core.service.UserService;
import com.youcode.s3cur1ty.security.provider.google.common.data.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class GoogleOpaqueTokenIntrospector implements OpaqueTokenIntrospector {
    private final WebClient userInfoClient;
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        UserInfo userInfo = userInfoClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("oauth2/v3/userinfo").queryParam("access_token", token).build())
                .retrieve()
                .bodyToMono(UserInfo.class)
                .block();
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("sub", userInfo.sub());
        attributes.put("name", userInfo.name());
        attributes.put("given_name", userInfo.given_name());
        attributes.put("family_name", userInfo.family_name());
        attributes.put("picture", userInfo.picture());
        attributes.put("email", userInfo.email());
        attributes.put("email_verified", userInfo.email_verified());
        attributes.put("locale", userInfo.locale());

        User user = userService.findByIdOrNULL(userInfo.sub());
        if (user == null) return new OAuth2IntrospectionAuthenticatedPrincipal(userInfo.name(), attributes, Collections.emptyList());
        else return new OAuth2IntrospectionAuthenticatedPrincipal(userInfo.name(), attributes, extractAuthorities(user.getRoles()));
    }

    private Collection<GrantedAuthority> extractAuthorities(Collection<Role> roles) {
    return roles.stream()
        .flatMap(role -> Stream.concat(
            Stream.of(new SimpleGrantedAuthority("ROLE_" + role.getName())),
            role.getPrivileges().stream().map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
        ))
        .collect(Collectors.toList());
}
}

