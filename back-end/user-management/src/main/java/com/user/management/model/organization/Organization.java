package com.user.management.model.organization;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.user.management.model.BaseEntity;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "organization")
public class Organization extends BaseEntity {

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "password")
    private String password;
}
