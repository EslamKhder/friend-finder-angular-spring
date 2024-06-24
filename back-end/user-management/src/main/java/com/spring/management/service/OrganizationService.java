package com.spring.management.service;

import com.spring.management.model.dto.auth.OrgDto;

import java.util.Map;

public interface OrganizationService {

    OrgDto create(Map<String, Object> params);
}
