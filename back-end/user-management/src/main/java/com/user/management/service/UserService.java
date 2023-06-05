package com.user.management.service;

import com.user.management.model.dto.auth.UserDto;

import javax.transaction.SystemException;
import java.util.Map;

public interface UserService {

    UserDto create(Map<String, Object> params) throws SystemException;
}
