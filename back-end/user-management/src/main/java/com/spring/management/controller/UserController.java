package com.spring.management.controller;

import java.util.Map;

import com.spring.commonlib.exceptions.SysException;
import com.spring.management.model.dto.auth.UserDto;
import com.spring.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@RequestBody Map<String, Object> params) throws SysException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(params));
    }

}
