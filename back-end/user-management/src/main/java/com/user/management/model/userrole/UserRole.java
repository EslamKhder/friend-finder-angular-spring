package com.user.management.model.userrole;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

import com.user.management.model.role.CompositeKey;
import com.user.management.model.role.Role;
import com.user.management.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user_role")
@NoArgsConstructor
public class UserRole implements Serializable {

    @EmbeddedId
    private CompositeKey compositeKey = new CompositeKey();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("integrationId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @MapsId("roleId")
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}
