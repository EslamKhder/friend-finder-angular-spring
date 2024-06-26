package com.spring.management.config.jwt;

import com.spring.management.sittings.Configurations;
import com.spring.management.model.user.User;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class AccessTokenUserHandler extends TokenHandler<User> {

    private Duration accessTokenTtl;

    private Duration refreshTokenTtl;

    /**
     * constructor to build JwtBuilder && JwtParser
     *
     * @param configurations
     */
    public AccessTokenUserHandler(Configurations configurations) {
        super(configurations);
        this.accessTokenTtl = configurations.getToken().getAccessTokenTime();
        this.refreshTokenTtl = configurations.getToken().getRefreshTokenTime();
    }

    @Override
    public String createToken(User user) {
        JwtBuilder tokenBuilder = createToken(user.getId().toString(), accessTokenTtl);
        tokenBuilder.claim("loginName", user.getName());
        tokenBuilder.claim("scope", user.getScope());
        return tokenBuilder.compact();
    }

    public String createRefreshToken(User user) {
        return createToken(user.getId().toString(), accessTokenTtl).compact();
    }

}
