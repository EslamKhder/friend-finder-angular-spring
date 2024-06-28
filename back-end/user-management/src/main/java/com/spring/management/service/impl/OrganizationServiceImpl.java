package com.spring.management.service.impl;

import com.spring.commonlib.exceptions.BusinessException;
import com.spring.commonlib.exceptions.FieldException;
import com.spring.commonlib.exceptions.SysException;
import com.spring.management.config.jwt.AccessTokenOrganizationHandler;
import com.spring.management.model.dto.auth.OrgDto;
import com.spring.management.model.dto.role.RoleDto;
import com.spring.commonlib.model.enums.Scope;
import com.spring.management.model.organization.Organization;
import com.spring.management.model.organizationrole.OrganizationRole;
import com.spring.management.repository.role.RoleRepository;
import com.spring.management.service.OrganizationService;
import com.spring.management.model.role.Role;
import com.spring.management.repository.organization.OrganizationRepository;
import com.spring.management.repository.organization.OrganizationRoleRepository;
import com.spring.management.service.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrganizationRoleRepository organizationRoleRepository;

    @Autowired
    private AccessTokenOrganizationHandler accessTokenOrganizationHandler;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProcedureService procedureService;

    private final String ORGANIZATION_ROLE_CODE = "DEFAULT_USER";



    @Override
    @Transactional
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

        OrgDto orgDto =  new OrgDto(organizationCreation.getId(), token, accessTokenOrganizationHandler.getExpireAt(token),accessTokenOrganizationHandler.createRefreshToken(organizationCreation),
                new RoleDto(role.get().getCode(), role.get().getDisplayName()), organizationCreation.getScope());

        procedureService.addUserToFriendFinder(orgDto.getOrgId(), orgDto.getScope().value());

        return orgDto;
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
