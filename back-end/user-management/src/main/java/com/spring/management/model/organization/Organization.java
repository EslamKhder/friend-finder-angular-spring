package com.spring.management.model.organization;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import com.spring.commonlib.model.BaseEntity;
import com.spring.management.model.organizationrole.OrganizationRole;
import com.spring.commonlib.model.enums.Scope;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "organization")
public class Organization extends BaseEntity {

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "password")
    private String password;

    @Column(name = "scope")
    @Enumerated(EnumType.STRING)
    private Scope scope;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "organization")
    private Set<OrganizationRole> roles = new HashSet();

    public Organization(String referenceId, String organizationName, String password, Scope scope) {
        this.referenceId = referenceId;
        this.organizationName = organizationName;
        this.password = password;
        this.scope = scope;
    }
}
