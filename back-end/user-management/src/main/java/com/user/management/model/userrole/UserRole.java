package com.user.management.model.userrole;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

import com.user.management.model.role.Role;
import com.user.management.model.user.User;

@Data
@Entity
@Table(name = "role_user")
public class UserRole implements Serializable {

    @EmbeddedId
    private UserRoleKey userRoleKey;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @MapsId("roleId")
    private Role role;
}
