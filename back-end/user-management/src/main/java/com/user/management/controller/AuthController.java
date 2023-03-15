package com.user.management.controller;

import java.util.Map;

import com.user.management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.management.model.dto.auth.AuthDto;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login/user")
    public ResponseEntity<AuthDto> loginUser(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(authService.authUser(params));
    }

    @GetMapping("/login/organization")
    public ResponseEntity<AuthDto> loginOrganization(
                    @RequestBody Map<String, Object> params) {
        return null;
    }

    @GetMapping("/token")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestBody String token) {
        return null;
    }
}
