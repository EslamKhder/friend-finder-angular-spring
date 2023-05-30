package com.user.management.config.jwt;

import com.user.management.sittings.Configurations;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

public abstract class TokenHandler<A> {

    protected JwtBuilder builder;

    protected JwtParser parser;

    /**
     * constructor to build JwtBuilder && JwtParser
     * @param configurations
     */
    public TokenHandler(Configurations configurations) {
        Key key = Keys.hmacShaKeyFor(configurations.getToken().getSecret().getBytes(StandardCharsets.UTF_8));

        this.builder = Jwts
            .builder()
            .signWith(key);

        this.parser = Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build();
    }

    /**
     * create token
     * @param subject
     * @param duration
     * @return JwtBuilder
     */
    protected JwtBuilder createToken(String subject, Duration duration) {
        Date issueAt = new Date(); // start date
        Date expiration = Date.from(issueAt.toInstant().plus(duration)); // expire date

        return builder
               .setSubject(subject)
               .setIssuedAt(issueAt)
               .setExpiration(expiration);
    }

    /**
     * to validate token
     * @param token
     * @return Boolean
     */
    public Boolean isValidToken(String token) {
        if (parser.isSigned(token)) {
            Claims claims = parser.parseClaimsJws(token).getBody();

            Date issueAt = claims.getIssuedAt();
            Date expiration = claims.getExpiration();

            return expiration.after(new Date()) && issueAt.before(new Date());
        }

        return false;
    }

    /**
     * get subject of token
     * @param token
     * @return subject
     */
    public String getSubject(String token) {
        return parser
               .parseClaimsJws(token)
               .getBody()
               .getSubject();
    }

    /**
     * create custom token
     * @param Param
     * @return token
     */
    public abstract String createToken(A Param);
}
