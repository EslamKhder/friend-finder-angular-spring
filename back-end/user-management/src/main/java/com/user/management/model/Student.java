package com.user.management.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Student {

    @Id
    Long id;

    String name;

    String age;
}
