package com.user.management.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import com.user.management.model.BaseEntity;
import com.user.management.model.enums.Language;
import com.user.management.model.enums.Scope;
import com.user.management.model.userrole.UserRole;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "user_system")
public class User extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "login_name")
    private String loginName;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "mobile_phone")
    private String mobilePhone;
    @Column(name = "admin")
    private boolean admin;
    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    private Language language;
    @Column(name = "scope")
    private Scope scope;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<UserRole> roles = new ArrayList<>();
}
