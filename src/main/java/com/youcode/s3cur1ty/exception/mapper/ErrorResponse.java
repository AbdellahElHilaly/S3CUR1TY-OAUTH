package com.youcode.s3cur1ty.exception.mapper;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@Data
public class ErrorResponse {
    private  LocalDateTime timestamp;
    private  String message;
    private  String path;


    private  Map<String, List<String>> details;

}
