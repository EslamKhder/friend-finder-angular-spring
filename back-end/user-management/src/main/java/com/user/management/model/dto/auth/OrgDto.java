package com.user.management.model.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user.management.model.dto.role.RoleDto;
import com.user.management.model.enums.Scope;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
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
    private Set<RoleDto> roles  = new HashSet();;

    @JsonProperty("scope")
    private Scope scope;

    public OrgDto(Long orgId, String accessToken, String expireAt, String refreshToken, RoleDto role, Scope scope) {
        this.orgId = orgId;
        this.accessToken = accessToken;
        this.expireAt = expireAt;
        this.refreshToken = refreshToken;
        this.scope = scope;
        roles.add(role);
    }
}
