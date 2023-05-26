package com.user.management.service;

import com.user.management.model.dto.auth.OrgAuthDto;
import com.user.management.model.dto.auth.UserAuthDto;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.util.Map;

@Service
public interface AuthService {

    /**
     * login with user
     * @param params
     * @return AuthDto
     */
    UserAuthDto authUser(Map<String, Object> params) throws SystemException;

    /**
     * login with organization
     * @param params
     * @return OrgAuthDto
     */
    OrgAuthDto authOrganization(Map<String, Object> params) throws SystemException;
}
