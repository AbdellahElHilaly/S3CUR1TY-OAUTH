package com.youcode.s3cur1ty.security.provider.google.core.controller;

import com.youcode.s3cur1ty.security.provider.google.common.data.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GooglePublicController {

    @GetMapping("/public")
    public ResponseEntity<MessageDto> publicMessages() {
        return ResponseEntity.ok(new MessageDto("public controller"));
    }
}
