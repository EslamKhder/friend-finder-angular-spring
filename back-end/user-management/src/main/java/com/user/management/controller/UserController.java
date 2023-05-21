package com.user.management.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.management.model.dto.auth.UserDto;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/create")
    public ResponseEntity<UserDto> create(@RequestBody Map<String, Object> params) {
        return null;
    }

}
