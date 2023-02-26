package com.niklai.oauth.oauth2.infrastructure.entity;

import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BaseEntity implements Serializable {

    @Column("created_at")
    private String createdAt;

    @Column("created_time")
    private LocalDateTime createdTime;

    @Column("updated_at")
    private String updatedAt;

    @Column("updated_time")
    private LocalDateTime updatedTime;

    @Column("del_flag")
    private Boolean delFlag = false;
}
