package com.spring.management.service;

import org.springframework.stereotype.Repository;

@Repository
public interface ProcedureService {

    /**
     * add User To Friend Finder
     * @param userId
     * @param scope
     */
    void addUserToFriendFinder(Long userId, String scope);
}
