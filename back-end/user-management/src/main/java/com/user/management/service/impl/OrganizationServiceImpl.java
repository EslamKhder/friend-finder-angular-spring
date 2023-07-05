package com.user.management.service.impl;

import com.user.management.exceptions.FieldException;
import com.user.management.model.dto.auth.UserDto;
import com.user.management.model.enums.Language;
import com.user.management.model.enums.Scope;
import com.user.management.service.OrganizationService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Override
    public UserDto create(Map<String, Object> params) {
        validateOrganizationFields(params);

        return null;
    }

    /**
     * validate Organization Fields
     * @param params
     */
    private static void validateOrganizationFields(Map<String, Object> params) {
        if (Objects.isNull(params.get("reference_id"))) {
            throw new FieldException("error.referenceId.required","#005","reference_id");
        }
        if (Objects.isNull(params.get("organization_name"))) {
            throw new FieldException("error.organizationName.required","#018","organization_name");
        }
        if (Objects.isNull(params.get("password"))) {
            throw new FieldException("error.password.required","#009","password");
        }
        if (Objects.isNull(params.get("scope"))) {
            throw new FieldException("error.scope.required","#013","scope");
        }

        if (
                !(
                        Scope.ORGANIZATION.value().equals(params.get("scope"))
                )
        ) {
            throw new FieldException("error.scope.invalid","#015","scope");
        }

    }
}
