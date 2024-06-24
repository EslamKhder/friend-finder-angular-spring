package com.spring.management.repository.organization;

import java.util.Optional;

import com.spring.management.model.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    /**
     * find Organization By Reference_Id
     * @param referenceId
     * @return Organization
     */
    Optional<Organization> findByReferenceId(String referenceId);
}
