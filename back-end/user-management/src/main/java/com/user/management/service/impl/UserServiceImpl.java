package com.user.management.service.impl;

import com.user.management.config.jwt.AccessTokenUserHandler;
import com.user.management.exceptions.BusinessException;
import com.user.management.exceptions.FieldException;
import com.user.management.exceptions.SysException;
import com.user.management.model.dto.auth.UserDto;
import com.user.management.model.dto.role.RoleDto;
import com.user.management.model.enums.Language;
import com.user.management.model.enums.Scope;
import com.user.management.model.role.Role;
import com.user.management.model.user.User;
import com.user.management.model.userrole.UserRole;
import com.user.management.repository.role.RoleRepository;
import com.user.management.repository.role.UserRoleRepository;
import com.user.management.repository.user.UserRepository;
import com.user.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;

    private AccessTokenUserHandler accessTokenUserHandler;

    private PasswordEncoder passwordEncoder;

    private final String USER_ROLE_CODE = "002";

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, AccessTokenUserHandler accessTokenUserHandler, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.accessTokenUserHandler = accessTokenUserHandler;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * create user
     * @param params
     * @return
     */
    @Override
    public UserDto create(Map<String, Object> params) throws SysException {

        validateUserFields(params);

        String name = params.get("name").toString();
        String loginName = params.get("loginName").toString();
        String email = params.get("email").toString();
        String password = params.get("password").toString();
        String mobilePhone = params.get("mobilePhone").toString();
        Language language = Language.valueOf(params.get("language").toString());
        Scope scope = Scope.valueOf(params.get("scope").toString());

        Optional<User> user = userRepository.findByLoginNameOrEmail(loginName, email);

        if (user.isPresent()) {
            throw new BusinessException("error.user.loginName.email.exist", "#016", "loginName | email");
        }

        User userCreation = new User(name, loginName, passwordEncoder.encode(password), email, mobilePhone, false, language, scope, true);

        userCreation = userRepository.save(userCreation);

        Optional<Role> role = roleRepository.findByCode(USER_ROLE_CODE);

        if (!role.isPresent()) {
            throw new SysException("role not exist", "#017");
        }

        UserRole userRole = new UserRole(userCreation, role.get());

        // add USER ROLE to user creation
        userRoleRepository.save(userRole);

        // create token
        String token =  accessTokenUserHandler.createToken(userCreation);

        return new UserDto(userCreation.getId(), token, accessTokenUserHandler.getExpireAt(token), accessTokenUserHandler.createRefreshToken(userCreation), new RoleDto(role.get().getCode(), role.get().getDisplayName()), userCreation.isAdmin(), userCreation.getLanguage(), userCreation.getScope());
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
                Scope.USER.value().equals(params.get("scope"))
            )
        ) {
            throw new FieldException("error.scope.invalid","#015","scope");
        }

    }
}
