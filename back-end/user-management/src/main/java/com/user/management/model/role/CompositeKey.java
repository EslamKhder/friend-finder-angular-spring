package com.user.management.model.role;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public abstract class CompositeKey implements Serializable {

    //@Column(name = "integration_id")
    private Long integrationId;

    //@Column(name = "role_id")
    private Long roleId;
}
