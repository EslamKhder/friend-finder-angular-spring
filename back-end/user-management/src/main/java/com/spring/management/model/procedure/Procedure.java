package com.spring.management.model.procedure;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Procedure {
    @Id
    private Long id;

}
