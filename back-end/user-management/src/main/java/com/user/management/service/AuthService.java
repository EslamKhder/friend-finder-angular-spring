package com.user.management.service;


import com.user.management.config.translate.BundleTranslator;
import com.user.management.exceptions.BadAuthException;
import com.user.management.exceptions.FieldException;
import com.user.management.model.dto.auth.AuthDto;
import com.user.management.model.user.User;
import com.user.management.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {


    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthDto authUser(Map<String, Object> params){
        String loginName = (String) params.get("loginName");
        String email = (String) params.get("email");
        String password = (String) params.get("password"); // 123456
        validateUserParam(loginName,email,password);
        User user = validateUserAuth(loginName, email, password);
        return new AuthDto(user.getId(),"token","expire","re_token",user.getRoles(),user.isAdmin(),user.getScope());
    }

    private User validateUserAuth(String loginName, String email, String password) {
        User user = (loginName != null) ? userRepository.findByLoginName(loginName) : userRepository.findByEmail(email);
        if (user == null){
            throw new BadAuthException("error.loginNameOrEmail.invalid","#003");
        }
        if (!passwordEncoder.matches(password,user.getPassword())){
            throw new BadAuthException("error.password.invalid","#004");
        }
        return user;
    }

    private void validateUserParam(String loginName, String email, String password) {
        if (loginName == null && email == null){
            throw new FieldException("error.parameter.emailOrLoginName.invalid","#001","email or loginName");
        }
        if(password == null){
            throw new FieldException("error.parameter.password.invalid","#002","Password");
        }
    }
}
