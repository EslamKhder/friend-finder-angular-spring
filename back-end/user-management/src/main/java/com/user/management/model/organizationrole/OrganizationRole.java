package com.user.management.model.organizationrole;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

import com.user.management.model.organization.Organization;
import com.user.management.model.role.CompositeKey;
import com.user.management.model.role.Role;

@Data
@Entity
@Table(name = "organization_role")
public class OrganizationRole implements Serializable {

    @EmbeddedId
    private CompositeKey compositeKey;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    @MapsId("integrationId")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @MapsId("roleId")
    private Role role;
}
