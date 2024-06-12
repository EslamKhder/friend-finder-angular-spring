package com.user.management.service.impl;


import com.user.management.config.jwt.AccessTokenOrganizationHandler;
import com.user.management.config.jwt.AccessTokenUserHandler;
import com.user.management.exceptions.BadAuthException;
import com.user.management.exceptions.FieldException;
import com.user.management.exceptions.SysException;
import com.user.management.model.dto.auth.OrgDto;
import com.user.management.model.dto.auth.UserDto;
import com.user.management.model.dto.role.RoleDto;
import com.user.management.model.enums.Scope;
import com.user.management.model.organization.Organization;
import com.user.management.model.user.User;
import com.user.management.repository.organization.OrganizationRepository;
import com.user.management.repository.user.UserRepository;
import com.user.management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
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
    public UserDto authUser(Map<String, Object> params) throws SysException {
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

        return new UserDto(user.getId(), token, accessTokenUserHandler.getExpireAt(token), accessTokenUserHandler.createRefreshToken(user), extractRoles(user), user.isAdmin(), user.getLanguage(), user.getScope());
    }

    /**
     * login with organization
     * @param params
     * @return OrgAuthDto
     */
    @Override
    public OrgDto authOrganization(Map<String, Object> params) throws SysException {
        // extract params of organization
        String referenceId = (String) params.get("reference_id");
        String password = (String) params.get("password");

        // validate params of organization
        validateOrganizationParam(referenceId, password);

        // validate user auth
        Organization organization = validateOrganizationAuth(referenceId, password);

        // create token
        String token =  accessTokenOrganizationHandler.createToken(organization);

        return new OrgDto(organization.getId(), token, accessTokenOrganizationHandler.getExpireAt(token), accessTokenOrganizationHandler.createRefreshToken(organization), extractRoles(organization), organization.getScope());
    }

    @Override
    public <T> Optional<T> authByToken(String token) {
        String scope = accessTokenUserHandler.getScope(token);

        if (Scope.USER.value().equals(scope)) {
            Long id  = Long.parseLong(accessTokenUserHandler.getSubject(token));
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                User existedUser = user.get();
                return (Optional<T>) Optional.of(new UserDto(existedUser.getId(), token, accessTokenUserHandler.getExpireAt(token), accessTokenUserHandler.createRefreshToken(existedUser), extractRoles(existedUser), existedUser.isAdmin(), existedUser.getLanguage(), existedUser.getScope()));
            }
        } else if (Scope.ORGANIZATION.value().equals(scope)) {
            Long id  = Long.parseLong(accessTokenOrganizationHandler.getSubject(token));
            Optional<Organization> organization = organizationRepository.findById(id);
            if (organization.isPresent()) {
                Organization existedOrganization = organization.get();
                return (Optional<T>) Optional.of(new OrgDto(existedOrganization.getId(), token, accessTokenOrganizationHandler.getExpireAt(token), accessTokenOrganizationHandler.createRefreshToken(existedOrganization), extractRoles(existedOrganization), existedOrganization.getScope()));
            }
        }

        throw new SysException("Scope out Of (USER, ORGANIZATION)", "#021");
    }

    /**
     * validate Organization Auth
     * @param referenceId
     * @param password
     * @return
     */
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
        if (Objects.isNull(referenceId)){
            throw new FieldException("error.referenceId.required","#005","referenceId");
        }
        if(Objects.isNull(password)){
            throw new FieldException("error.password.required","#006","Password");
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
        if (Objects.isNull(loginName) && Objects.isNull(email)){
            throw new FieldException("error.emailOrLoginName.required","#001","email or loginName");
        }
        if(Objects.isNull(password)){
            throw new FieldException("error.password.required","#002","Password");
        }
    }

    /**
     * extract roles
     * @param userType
     */
    private <T> Set<RoleDto> extractRoles(T userType) throws SysException { // OrganizationRole  UserRole

        if (!(userType instanceof User || userType instanceof Organization)) {
            throw new SysException("to extract roles you must send User OR Organization", "#018");
        }

        if (userType instanceof User) {
            return ((User)userType).getRoles().stream().map(organizationRole ->
                    new RoleDto(organizationRole.getRole().getCode(),
                            organizationRole.getRole().getDisplayName())).collect(Collectors.toSet());
        }

        if (userType instanceof Organization) {
            return ((Organization)userType).getRoles().stream().map(organizationRole ->
                    new RoleDto(organizationRole.getRole().getCode(),
                            organizationRole.getRole().getDisplayName())).collect(Collectors.toSet());
        }

        return null;
    }
}
