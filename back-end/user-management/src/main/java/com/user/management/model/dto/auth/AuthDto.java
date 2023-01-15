package com.user.management.model.dto.auth;

import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user.management.model.enums.Scope;

@Data
public class AuthDto {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expire_at")
    private String expireAt;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private List<String> roles;
    private boolean admin;
    private Scope scope;
}
