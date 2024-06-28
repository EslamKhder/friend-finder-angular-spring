package com.spring.management.service.impl;

import com.spring.management.repository.procedure.ProcedureRepository;
import com.spring.management.service.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProcedureServiceImpl implements ProcedureService {

    @Autowired
    private ProcedureRepository procedureRepository;

    @Override
    public void addUserToFriendFinder(Long userId, String scope) {
        procedureRepository.addUserToFriendFinder(userId, scope);
    }
}
