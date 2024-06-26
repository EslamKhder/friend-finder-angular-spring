package com.spring.management.model.role;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import com.spring.commonlib.model.BaseEntity;
import com.spring.management.model.userrole.UserRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    private String code;

    private String displayName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    List<UserRole> userRoles = new ArrayList<>();
}
