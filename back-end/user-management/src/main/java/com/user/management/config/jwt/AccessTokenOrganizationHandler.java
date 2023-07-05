package com.user.management.config.jwt;

import com.user.management.model.organization.Organization;
import com.user.management.model.user.User;
import com.user.management.sittings.Configurations;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class AccessTokenOrganizationHandler extends TokenHandler<Organization> {

    private Duration accessTokenTtl;

    private Duration refreshTokenTtl;

    /**
     * constructor to build JwtBuilder && JwtParser
     *
     * @param configurations
     */
    @Autowired
    public AccessTokenOrganizationHandler(Configurations configurations) {
        super(configurations);
        this.accessTokenTtl = configurations.getToken().getAccessTokenTime();
        this.refreshTokenTtl = configurations.getToken().getRefreshTokenTime();
    }

    @Override
    public String createToken(Organization organization) {
        JwtBuilder tokenBuilder = createToken(organization.getId().toString(), accessTokenTtl);
        tokenBuilder.claim("referenceId", organization.getReferenceId());
        tokenBuilder.claim("scope", organization.getScope());
        return tokenBuilder.compact();
    }

    /**
     * get token Expire At
     * @param token
     * @return Date
     */
    public String getExpireAt(String token){
        return parser.parseClaimsJws(token).getBody().getExpiration().toString();
    }

    public String createRefreshToken(Organization organization) {
        return createToken(organization.getId().toString(), accessTokenTtl).compact();
    }

}
