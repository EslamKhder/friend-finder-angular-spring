package com.spring.management.service;

import org.springframework.stereotype.Repository;

@Repository
public interface ProcedureService {

    void addUserToFriendFinder(Long userId, String scope);
}
