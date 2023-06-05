package com.user.management.model.dto.auth;

import com.user.management.model.dto.role.RoleDto;
import com.user.management.model.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user.management.model.enums.Scope;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expire_at")
    private String expireAt;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private List<RoleDto> roles;
    private boolean admin;
    private Language language;
    private Scope scope;

    public UserDto(Long userId, String accessToken, String expireAt, String refreshToken, List<RoleDto> roles, boolean admin, Language language, Scope scope) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.expireAt = expireAt;
        this.refreshToken = refreshToken;
        this.roles = roles;
        this.admin = admin;
        this.language = language;
        this.scope = scope;
    }

}
