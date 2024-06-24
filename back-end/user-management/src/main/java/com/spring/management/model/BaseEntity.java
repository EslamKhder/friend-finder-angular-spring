package com.spring.management.model;

import com.spring.commonlib.config.audit.EntityAuditingEventListener;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = false)
@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
@Audited
@EntityListeners({ AuditingEntityListener.class, EntityAuditingEventListener.class })
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Basic(optional = false)
    private Long id;

    private boolean active;

    @CreatedDate
    @Column(name = "created_time", updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;

    public BaseEntity(boolean active) {
        this.active = active;
    }

    // private Long modifiedBy;
}
