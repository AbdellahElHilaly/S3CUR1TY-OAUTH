package com.youcode.s3cur1ty.security.provider.google.core.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.youcode.s3cur1ty.security.provider.google.common.data.dto.UserInfo;
import com.youcode.s3cur1ty.security.provider.google.core.service.OAuth2GoogleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class OAuth2GoogleServiceImpl implements OAuth2GoogleService {

    @Value("${spring.security.oauth2.resource-server.opaque-token.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.resource-server.opaque-token.client-secret}")
    private String clientSecret;

    private final WebClient userInfoClient;


    @Override
    public UserInfo extractUserInfo(OAuth2AuthenticatedPrincipal principal) {
        Map<String, Object> attributes = principal.getAttributes();
        return new UserInfo(
                (String) attributes.get("sub"),
                (String) attributes.get("name"),
                (String) attributes.get("given_name"),
                (String) attributes.get("family_name"),
                (String) attributes.get("picture"),
                (String) attributes.get("email"),
                (boolean) attributes.get("email_verified"),
                (String) attributes.get("locale")
        );
    }

    @Override
    public String generateToken(String code) {
        String token;
        try {
            token = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(), new GsonFactory(),
                    clientId,
                    clientSecret,
                    code,
                    "http://localhost:4200"
            ).execute().getAccessToken();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
        return token;
    }

    @Override
    public String generateUrl() {
        return new GoogleAuthorizationCodeRequestUrl(
                clientId,
                "http://localhost:4200",
                Arrays.asList("email", "profile", "openid")
        ).build();
    }

    @Override
    public UserInfo getUserInfo(String token) {
         return userInfoClient
                .get()
                .uri("https://www.googleapis.com/oauth2/v3/userinfo")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(UserInfo.class)
                .block();
    }


}
