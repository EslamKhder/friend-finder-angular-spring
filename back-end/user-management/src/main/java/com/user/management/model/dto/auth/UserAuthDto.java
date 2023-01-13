package com.user.management.model.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user.management.model.enums.Language;

public class UserAuthDto {

    private String name;
    @JsonProperty("login_name")
    private String loginName;
    private String email;
    @JsonProperty("mobile_phone")
    private String mobilePhone;
    private boolean admin;
    private Language language;
}
