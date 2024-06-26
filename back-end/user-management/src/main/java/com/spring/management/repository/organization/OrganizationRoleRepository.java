package com.spring.management.repository.organization;

import com.spring.management.model.organizationrole.OrganizationRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRoleRepository extends JpaRepository<OrganizationRole, Long> {

}
