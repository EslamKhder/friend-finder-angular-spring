package com.user.management.repository.organization;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.management.model.organization.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    /**
     * find Organization By Reference_Id
     * @param referenceId
     * @return Organization
     */
    Optional<Organization> findByReferenceId(String referenceId);
}
