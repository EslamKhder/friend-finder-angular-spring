package com.user.management.controller;

import java.util.Map;

import com.user.management.exceptions.SysException;
import com.user.management.model.dto.auth.OrgDto;
import com.user.management.service.AuthService;
import com.user.management.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.management.model.dto.auth.UserDto;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login/user")
    public ResponseEntity<UserDto> loginUser(@RequestBody Map<String, Object> params) throws SysException {
        return ResponseEntity.ok(authService.authUser(params));
    }

    @GetMapping("/login/organization")
    public ResponseEntity<OrgDto> loginOrganization(
                    @RequestBody Map<String, Object> params) throws SysException {

        return ResponseEntity.ok(authService.authOrganization(params));
    }

    @GetMapping("/token")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestBody String token) {
        return null;
    }
}
