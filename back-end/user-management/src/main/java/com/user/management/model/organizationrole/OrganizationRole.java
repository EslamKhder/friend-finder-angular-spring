package com.user.management.model.organizationrole;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    public OrganizationRole(Organization organization, Role role) {
        this.organization = organization;
        this.role = role;
    }
}
