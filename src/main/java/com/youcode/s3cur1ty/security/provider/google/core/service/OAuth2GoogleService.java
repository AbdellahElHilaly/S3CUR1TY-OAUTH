package com.youcode.s3cur1ty.security.provider.google.core.service;

import com.youcode.s3cur1ty.security.provider.google.common.data.dto.UserInfo;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;


public interface OAuth2GoogleService {

    public UserInfo extractUserInfo(OAuth2AuthenticatedPrincipal principal);

    String generateToken(String code);

    String generateUrl();

    UserInfo getUserInfo(String token);
}
