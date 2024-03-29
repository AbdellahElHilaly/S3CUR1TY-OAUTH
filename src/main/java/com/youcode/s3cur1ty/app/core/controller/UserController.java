package com.youcode.s3cur1ty.app.core.controller;

import com.youcode.s3cur1ty.app.core.database.model.entity.User;
import com.youcode.s3cur1ty.app.core.database.model.wrapper.UserDetail;
import com.youcode.s3cur1ty.app.core.service.UserService;
import com.youcode.s3cur1ty.security.provider.google.common.data.dto.UserInfo;
import com.youcode.s3cur1ty.security.provider.google.core.service.OAuth2GoogleService;
import com.youcode.s3cur1ty.utils.Const.AppEndPoints;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppEndPoints.USER)
public class UserController {

    private final OAuth2GoogleService oAuth2GoogleService;
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> profile(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        UserInfo userInfo = oAuth2GoogleService.extractUserInfo(principal);

        User user = userService.findByID(userInfo.sub());
        user.setUserDetail(UserDetail.fromUserInfo(userInfo));
        return ResponseEntity.ok(user);
    }

//    @PreAuthorize(value = "(hasRole('SUPER_ADMIN')) and hasAuthority('CAN_EDIT')")
    @GetMapping("/authorities")
    public ResponseEntity<List<String>> getAuthorities(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(authority -> System.out.println("Authority: " + authority.getAuthority()));
        return ResponseEntity.status(HttpStatus.OK).body(authentication.getAuthorities().stream().map(authority -> authority.getAuthority()).toList());
    }

    @GetMapping("/list")
    @PreAuthorize(value = "(hasRole('SUPER_ADMIN')) and hasAuthority('CAN_SHOW')")
    public ResponseEntity<List<User>> list() {
        return ResponseEntity.ok(userService.findAll());
    }


}
