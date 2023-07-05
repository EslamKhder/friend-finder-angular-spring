package com.user.management.service;

import com.user.management.model.dto.auth.OrgDto;

import java.util.Map;

public interface OrganizationService {

    OrgDto create(Map<String, Object> params);
}
