package com.youcode.s3cur1ty.security.provider.google.core.controller;

import com.youcode.s3cur1ty.app.core.database.model.entity.User;
import com.youcode.s3cur1ty.app.core.database.model.wrapper.UserDetail;
import com.youcode.s3cur1ty.app.core.service.UserService;
import com.youcode.s3cur1ty.security.provider.google.common.data.dto.TokenDto;
import com.youcode.s3cur1ty.security.provider.google.common.data.dto.UrlDto;
import com.youcode.s3cur1ty.security.provider.google.common.data.dto.UserInfo;
import com.youcode.s3cur1ty.security.provider.google.core.service.OAuth2GoogleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OAuth2GoogleService oAuth2GoogleService;
    private final UserService userService;


    @GetMapping("/auth/url")
    public ResponseEntity<UrlDto> authUrl() {
        return ResponseEntity.ok(new UrlDto(oAuth2GoogleService.generateUrl()));
    }

    @GetMapping("/auth/callback")
    public ResponseEntity<TokenDto> callback(@RequestParam("code") String code) throws URISyntaxException {

        String token = oAuth2GoogleService.generateToken(code);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserInfo userInfo = oAuth2GoogleService.getUserInfo(token);

        userService.save(User.builder()
                .sub(userInfo.sub())
                .userDetail(UserDetail.fromUserInfo(userInfo))
                .build());

        return ResponseEntity.ok(new TokenDto(token));
    }

}
