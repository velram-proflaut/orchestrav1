package com.pro.orchestra.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app_groups")
public class AppGroup extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
}