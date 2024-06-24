package com.spring.management.service;

import com.spring.management.model.dto.auth.UserDto;

import java.util.Map;

public interface UserService {

    UserDto create(Map<String, Object> params);
}
