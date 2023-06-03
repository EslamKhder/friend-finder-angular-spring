package com.user.management.service.impl;

import com.user.management.exceptions.FieldException;
import com.user.management.model.dto.auth.UserDto;
import com.user.management.model.enums.Language;
import com.user.management.model.enums.Scope;
import com.user.management.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {


    /**
     * create user
     * @param params
     * @return
     */
    @Override
    public UserDto create(Map<String, Object> params) {

        validateUserFields(params);

        return null;
    }

    /**
     * validate User Fields
     * @param params
     */
    private static void validateUserFields(Map<String, Object> params) {
        if (Objects.isNull(params.get("name"))) {
            throw new FieldException("error.name.required","#007","name");
        }
        if (Objects.isNull(params.get("loginName"))) {
            throw new FieldException("error.loginName.required","#008","loginName");
        }
        if (Objects.isNull(params.get("password"))) {
            throw new FieldException("error.password.required","#009","password");
        }
        if (Objects.isNull(params.get("email"))) {
            throw new FieldException("error.email.required","#010","email");
        }
        if (Objects.isNull(params.get("mobilePhone"))) {
            throw new FieldException("error.mobilePhone.required","#011","mobilePhone");
        }
        if (Objects.isNull(params.get("language"))) {
            throw new FieldException("error.language.required","#012","language");
        }
        if (Objects.isNull(params.get("scope"))) {
            throw new FieldException("error.scope.required","#013","scope");
        }

        if (
            !(
                Language.ARABIC.value().equals(params.get("language")) ||
                Language.ENGLISH.value().equals(params.get("language"))
            )
          ) {
            throw new FieldException("error.language.invalid","#014","language");
        }

        if (
            !(
                Scope.USER.value().equals(params.get("scope")) ||
                Scope.ORGANIZATION.value().equals(params.get("scope"))
            )
        ) {
            throw new FieldException("error.scope.invalid","#015","scope");
        }

    }
}
