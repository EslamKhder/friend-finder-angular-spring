package com.user.management.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseEntity {

    private Long id;

    private boolean active;

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    private Long modifiedBy;
}
