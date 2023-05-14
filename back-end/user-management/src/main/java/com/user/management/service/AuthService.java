package com.user.management.service;

import com.user.management.model.dto.auth.AuthDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AuthService {

    /**
     * login with user
     * @param params
     * @return AuthDto
     */
    AuthDto authUser(Map<String, Object> params);
}
