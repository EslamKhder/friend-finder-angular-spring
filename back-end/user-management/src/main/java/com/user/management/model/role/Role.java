package com.user.management.model.role;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.user.management.model.BaseEntity;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    private String code;// admin

    private String displayName;// Admin
}
