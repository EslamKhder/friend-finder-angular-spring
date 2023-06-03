package com.user.management.model.dto.auth;

import com.user.management.model.dto.role.RoleDto;
import com.user.management.model.userrole.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user.management.model.enums.Scope;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDto {
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
    private Scope scope;
}
