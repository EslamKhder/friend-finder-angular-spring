package com.spring.management.model.role;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class CompositeKey implements Serializable {

    //@Column(name = "integration_id")
    private Long integrationId;

    //@Column(name = "role_id")
    private Long roleId;
}
