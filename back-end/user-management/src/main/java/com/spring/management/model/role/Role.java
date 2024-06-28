package com.spring.management.model.role;

import javax.persistence.*;
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

    @Column(unique = true)
    private String code;

    private String displayName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    List<UserRole> userRoles = new ArrayList<>();
}
