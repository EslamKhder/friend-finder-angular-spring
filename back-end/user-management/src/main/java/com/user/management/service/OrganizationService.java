package com.user.management.service;

import com.user.management.model.dto.auth.UserDto;

import java.util.Map;

public interface OrganizationService {

    UserDto create(Map<String, Object> params);
}
