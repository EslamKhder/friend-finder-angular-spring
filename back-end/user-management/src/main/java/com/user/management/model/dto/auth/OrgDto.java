package com.user.management.model.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user.management.model.dto.role.RoleDto;
import com.user.management.model.enums.Scope;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgDto {
    @JsonProperty("org_id")
    private Long orgId;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expire_at")
    private String expireAt;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("roles")
    private List<RoleDto> roles;

    @JsonProperty("scope")
    private Scope scope;
}
