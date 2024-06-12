package com.user.management.config;

import com.user.management.config.jwt.AccessTokenOrganizationHandler;
import com.user.management.config.jwt.AccessTokenUserHandler;
import com.user.management.model.dto.auth.OrgDto;
import com.user.management.model.dto.auth.UserDto;
import com.user.management.model.dto.role.RoleDto;
import com.user.management.model.organization.Organization;
import com.user.management.model.user.User;
import com.user.management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AccessTokenUserHandler accessTokenUserHandler;

    @Autowired
    private AccessTokenOrganizationHandler accessTokenOrganizationHandler;

    @Autowired
    private AuthService authService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        return checkPath(path, SecurityConfig.PUBLIC_APIS);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        // Authorization Header Type Basic AUTH
        Optional<String> basicAuthorization =
                Optional.ofNullable(authorizationHeader).filter(header -> header.startsWith("Basic "));

        if (basicAuthorization.isPresent()) {
            filterChain.doFilter(request, response);
            return;
        }

        // Authorization Header Type JWT Bearer Token
        Optional<String> jwt =
                Optional.ofNullable(authorizationHeader).filter(header -> header.startsWith("Bearer "))
                                .map(header -> header.substring(7)).filter(accessTokenUserHandler::isValidToken);

        if (!jwt.isPresent()){
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Optional<Object> auth = jwt.flatMap(authService::authByToken);

        if (!auth.isPresent()) {
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        if (auth.get() instanceof UserDto){
            UserDto userDto = (UserDto) auth.get();

            usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDto, null, extractRoles(userDto.getRoles()));
        } else if (auth.get() instanceof OrgDto) {
            OrgDto orgDto = (OrgDto) auth.get();

            usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(orgDto, null, extractRoles(orgDto.getRoles()));

        }

        usernamePasswordAuthenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }

    /**
     * check Path
     * @param path
     * @param paths
     * @return
     */
    private Boolean checkPath(String path, String [] paths){
        String[] parts = path.split("/");
        String newPath = "/" + parts[1] + "/**";

        return Arrays.asList(paths).contains(newPath);
    }

    /**
     * extract Roles
     * @param roles
     * @return
     */
    private List<SimpleGrantedAuthority> extractRoles(Set<RoleDto> roles){
        return  (roles != null) ?
                roles.stream()
                        .map(role -> new SimpleGrantedAuthority(role.getCode()))
                        .collect(Collectors.toList())
                : Collections.emptyList();
    }
}
