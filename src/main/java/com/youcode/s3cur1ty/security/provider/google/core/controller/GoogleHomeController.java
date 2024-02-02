package com.youcode.s3cur1ty.security.provider.google.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoogleHomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
