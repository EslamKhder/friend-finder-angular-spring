package com.user.management.service;


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
        validateUserAuth(loginName,email,password);
        User user;
        if (loginName != null){
            user = userRepository.findByLoginName(loginName);
        } else {
            user = userRepository.findByEmail(email);
        }
        if (user == null){
            throw new BadCredentialsException("Invalid login Name or Email");
        }
        if (!passwordEncoder.matches(password,user.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }
        return toAuthDto(user);
    }

    private AuthDto toAuthDto(User user) {
        AuthDto authDto = new AuthDto();
        authDto.setUserId(user.getId());
        authDto.setAccessToken("token");
        authDto.setAdmin(user.isAdmin());
        authDto.setRefreshToken("re_token");
        authDto.setRoles(user.getRoles());
        authDto.setExpireAt("expire");
        authDto.setScope(user.getScope());

        return authDto;
    }

    private void validateUserAuth(String loginName, String email, String password) {
        if (loginName == null && email == null){
            throw new BadCredentialsException("you must enter email or loginName");
        }
        if(password == null){
            throw new BadCredentialsException("you must enter Password");
        }
    }
}
