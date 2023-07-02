package com.user.management.service;

import com.user.management.model.dto.auth.OrgDto;
import com.user.management.model.dto.auth.UserDto;
import org.springframework.stereotype.Service;

import java.util.Map;

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
}
