package com.user.management.service.impl;


import com.user.management.exceptions.BadAuthException;
import com.user.management.exceptions.FieldException;
import com.user.management.model.dto.auth.AuthDto;
import com.user.management.model.user.User;
import com.user.management.repository.user.UserRepository;
import com.user.management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * login with user
     * @param params
     * @return AuthDto
     */
    @Override
    public AuthDto authUser(Map<String, Object> params){
        // extract params of users
        String loginName = (String) params.get("loginName");
        String email = (String) params.get("email");
        String password = (String) params.get("password");

        // validate params of user
        validateUserParam(loginName,email,password);

        // validate user auth
        User user = validateUserAuth(loginName, email, password);

        return new AuthDto(user.getId(),"token","expire","re_token",user.getRoles(),user.isAdmin(),user.getScope());
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
}
