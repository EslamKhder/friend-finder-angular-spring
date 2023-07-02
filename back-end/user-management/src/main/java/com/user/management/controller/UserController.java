package com.user.management.controller;

import java.util.Map;

import com.user.management.exceptions.SysException;
import com.user.management.model.dto.auth.UserDto;
import com.user.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@RequestBody Map<String, Object> params) throws SysException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(params));
    }

}
