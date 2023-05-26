package com.user.management.controller;

import java.util.Map;

import com.user.management.model.dto.auth.OrgAuthDto;
import com.user.management.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.management.model.dto.auth.UserAuthDto;

import javax.transaction.SystemException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthServiceImpl authService;

    @Autowired
    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @GetMapping("/login/user")
    public ResponseEntity<UserAuthDto> loginUser(@RequestBody Map<String, Object> params) throws SystemException {
        return ResponseEntity.ok(authService.authUser(params));
    }

    @GetMapping("/login/organization")
    public ResponseEntity<OrgAuthDto> loginOrganization(
                    @RequestBody Map<String, Object> params) throws SystemException {

        return ResponseEntity.ok(authService.authOrganization(params));
    }

    @GetMapping("/token")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestBody String token) {
        return null;
    }
}
