package com.petlink.common.domain.user;

import com.petlink.common.domain.base.BaseTimeEntity;
import com.petlink.common.util.jwt.UserRole;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseUser extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "id")
    protected Long id;

    @Column(unique = true, name = "email", nullable = false)
    protected String email;

    @Column(name = "name", nullable = false)
    protected String name;

    @Column
    @Enumerated(EnumType.STRING)
    protected UserRole role;
}
