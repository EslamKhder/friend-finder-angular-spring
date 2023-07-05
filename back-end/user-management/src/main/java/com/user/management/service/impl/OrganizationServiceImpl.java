package com.user.management.service.impl;

import com.user.management.config.jwt.AccessTokenOrganizationHandler;
import com.user.management.exceptions.BusinessException;
import com.user.management.exceptions.FieldException;
import com.user.management.exceptions.SysException;
import com.user.management.model.dto.auth.OrgDto;
import com.user.management.model.dto.auth.UserDto;
import com.user.management.model.dto.role.RoleDto;
import com.user.management.model.enums.Language;
import com.user.management.model.enums.Scope;
import com.user.management.model.organization.Organization;
import com.user.management.model.organizationrole.OrganizationRole;
import com.user.management.model.role.Role;
import com.user.management.repository.organization.OrganizationRepository;
import com.user.management.repository.organization.OrganizationRoleRepository;
import com.user.management.repository.role.RoleRepository;
import com.user.management.service.OrganizationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;

    private PasswordEncoder passwordEncoder;

    private OrganizationRoleRepository organizationRoleRepository;

    private AccessTokenOrganizationHandler accessTokenOrganizationHandler;

    private RoleRepository roleRepository;

    private final String ORGANIZATION_ROLE_CODE = "001";

    @Override
    public OrgDto create(Map<String, Object> params) {
        validateOrganizationFields(params);

        String referenceId = params.get("reference_id").toString();
        String organizationName = params.get("organization_name").toString();
        String password = params.get("password").toString();
        Scope scope = Scope.valueOf(params.get("scope").toString());

        Optional<Organization> organization = organizationRepository.findByReferenceId(referenceId);

        if (organization.isPresent()) {
            throw new BusinessException("error.organization.referenceId.exist", "#020", "loginName | email");
        }

        Organization organizationCreation = new Organization(referenceId, organizationName, passwordEncoder.encode(password), scope);

        organizationCreation = organizationRepository.save(organizationCreation);

        Optional<Role> role = roleRepository.findByCode(ORGANIZATION_ROLE_CODE);

        if (!role.isPresent()) {
            throw new SysException("role not exist", "#017");
        }

        OrganizationRole organizationRole = new OrganizationRole(organizationCreation, role.get());

        // add ORGANIZATION ROLE to organization creation
        organizationRoleRepository.save(organizationRole);

        // create token
        String token =  accessTokenOrganizationHandler.createToken(organizationCreation);

        return new OrgDto(organizationCreation.getId(), token, accessTokenOrganizationHandler.getExpireAt(token),accessTokenOrganizationHandler.createRefreshToken(organizationCreation),
                new RoleDto(role.get().getCode(), role.get().getDisplayName()), organizationCreation.getScope());
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
            throw new FieldException("error.scope.organization.invalid","#019","scope");
        }

    }
}
