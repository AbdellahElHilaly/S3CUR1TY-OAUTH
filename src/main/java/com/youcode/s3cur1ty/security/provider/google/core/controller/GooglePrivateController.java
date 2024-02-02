package com.youcode.s3cur1ty.security.provider.google.core.controller;

import com.youcode.s3cur1ty.security.provider.google.common.data.dto.MessageDto;
import com.youcode.s3cur1ty.security.provider.google.common.data.dto.UserInfo;
import com.youcode.s3cur1ty.security.provider.google.core.service.OAuth2GoogleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequiredArgsConstructor
public class GooglePrivateController {
    private final OAuth2GoogleService oAuth2GoogleService;

    @GetMapping("/messages")
    public ResponseEntity<MessageDto> privateMessages(@AuthenticationPrincipal(expression = "name") String name, Model model) {
        return ResponseEntity.ok(new MessageDto("Hello " + name));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserInfo> privateMessages(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return ResponseEntity.ok(oAuth2GoogleService.extractUserInfo(principal));
    }


}
