package com.user.management.service.impl;


import com.user.management.config.jwt.AccessTokenOrganizationHandler;
import com.user.management.config.jwt.AccessTokenUserHandler;
import com.user.management.exceptions.BadAuthException;
import com.user.management.exceptions.FieldException;
import com.user.management.model.dto.auth.OrgAuthDto;
import com.user.management.model.dto.auth.UserAuthDto;
import com.user.management.model.dto.role.RoleDto;
import com.user.management.model.organization.Organization;
import com.user.management.model.organizationrole.OrganizationRole;
import com.user.management.model.user.User;
import com.user.management.model.userrole.UserRole;
import com.user.management.repository.organization.OrganizationRepository;
import com.user.management.repository.user.UserRepository;
import com.user.management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private OrganizationRepository organizationRepository;

    private AccessTokenUserHandler accessTokenUserHandler;

    private AccessTokenOrganizationHandler accessTokenOrganizationHandler;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, OrganizationRepository organizationRepository, AccessTokenUserHandler accessTokenUserHandler, AccessTokenOrganizationHandler accessTokenOrganizationHandler) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.organizationRepository = organizationRepository;
        this.accessTokenUserHandler = accessTokenUserHandler;
        this.accessTokenOrganizationHandler = accessTokenOrganizationHandler;
    }

    /**
     * login with user
     * @param params
     * @return AuthDto
     */
    @Override
    public UserAuthDto authUser(Map<String, Object> params) throws SystemException {
        // extract params of users
        String loginName = (String) params.get("loginName");
        String email = (String) params.get("email");
        String password = (String) params.get("password");

        // validate params of user
        validateUserParam(loginName,email,password);

        // validate user auth
        User user = validateUserAuth(loginName, email, password);

        // create token
        String token = accessTokenUserHandler.createToken(user);

        return new UserAuthDto(user.getId(), token, accessTokenUserHandler.getExpireAt(token), accessTokenUserHandler.createRefreshToken(user), extractRoles(user), user.isAdmin(), user.getScope());
    }

    /**
     * login with organization
     * @param params
     * @return OrgAuthDto
     */
    @Override
    public OrgAuthDto authOrganization(Map<String, Object> params) throws SystemException {
        // extract params of organization
        String referenceId = (String) params.get("reference_id");
        String password = (String) params.get("password");

        // validate params of organization
        validateOrganizationParam(referenceId, password);

        // validate user auth
        Organization organization = validateOrganizationAuth(referenceId, password);

        // create token
        String token =  accessTokenOrganizationHandler.createToken(organization);

        return new OrgAuthDto(organization.getId(), token, accessTokenUserHandler.getExpireAt(token), accessTokenOrganizationHandler.createRefreshToken(organization), extractRoles(organization), organization.getScope());
    }

    private Organization validateOrganizationAuth(String referenceId, String password) {
        Optional<Organization> organization =  organizationRepository.findByReferenceId(referenceId);
        if (!organization.isPresent()){
            throw new BadAuthException("error.referenceId.invalid","#007");
        }
        if (!passwordEncoder.matches(password,organization.get().getPassword())){
            throw new BadAuthException("error.password.invalid","#008");
        }
        return organization.get();
    }

    /**
     * validate organization param
     * @param referenceId
     * @param password
     */
    private void validateOrganizationParam(String referenceId, String password) {
        if (referenceId == null ){
            throw new FieldException("error.parameter.referenceId.invalid","#005","referenceId");
        }
        if(password == null){
            throw new FieldException("error.parameter.password.invalid","#006","Password");
        }
    }

    /**
     * validate User Auth
     * @param loginName
     * @param email
     * @param password
     * @return User
     */
    private User validateUserAuth(String loginName, String email, String password) {
        Optional<User> user = (loginName != null) ? userRepository.findByLoginName(loginName) : userRepository.findByEmail(email);
        if (!user.isPresent()){
            throw new BadAuthException("error.loginNameOrEmail.invalid","#003");
        }
        if (!passwordEncoder.matches(password,user.get().getPassword())){
            throw new BadAuthException("error.password.invalid","#004");
        }
        return user.get();
    }

    /**
     * validate user param
     * @param loginName
     * @param email
     * @param password
     */
    private void validateUserParam(String loginName, String email, String password) {
        if (loginName == null && email == null){
            throw new FieldException("error.parameter.emailOrLoginName.invalid","#001","email or loginName");
        }
        if(password == null){
            throw new FieldException("error.parameter.password.invalid","#002","Password");
        }
    }

    /**
     * extract roles
     * @param roles
     */
    private List<RoleDto> extractOrganizationRole(List<OrganizationRole> roles) {
        return roles.stream().map(organizationRole ->
                new RoleDto(organizationRole.getRole().getCode(),
                        organizationRole.getRole().getDisplayName())).collect(Collectors.toList());
    }

    /**
     * extract roles
     * @param userType
     */
    private <T> List<RoleDto> extractRoles(T userType) throws SystemException { // OrganizationRole  UserRole

        if (!(userType instanceof User || userType instanceof Organization)) {
            throw new SystemException("to extract roles you must send User OR Organization");
        }

        if (userType instanceof User) {
            return ((User)userType).getRoles().stream().map(organizationRole ->
                    new RoleDto(organizationRole.getRole().getCode(),
                            organizationRole.getRole().getDisplayName())).collect(Collectors.toList());
        }

        if (userType instanceof Organization) {
            return ((Organization)userType).getRoles().stream().map(organizationRole ->
                    new RoleDto(organizationRole.getRole().getCode(),
                            organizationRole.getRole().getDisplayName())).collect(Collectors.toList());
        }

        return null;
    }
}
