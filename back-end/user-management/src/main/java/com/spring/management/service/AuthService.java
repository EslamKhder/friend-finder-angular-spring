package com.spring.management.service;

import com.spring.management.model.dto.auth.OrgDto;
import com.spring.management.model.dto.auth.UserDto;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public interface AuthService {

    /**
     * login with user
     * @param params
     * @return AuthDto
     */
    UserDto authUser(Map<String, Object> params);

    /**
     * login with organization
     * @param params
     * @return OrgAuthDto
     */
    OrgDto authOrganization(Map<String, Object> params);

    /**
     * auth By Token
     * @param token
     * @return
     * @param <T>
     */
    <T> Optional<T> authByToken(String token);
}
