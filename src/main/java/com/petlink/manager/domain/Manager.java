package com.petlink.manager.domain;

import com.petlink.common.util.jwt.Role;
import com.petlink.funding.domain.Funding;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "manager")
public class Manager {
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
    protected Role role;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "office_number", nullable = false)
    private String officeNumber;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Funding> fundingList;

    protected Manager() {
    }

    @Builder
    public Manager(Long id, String email, String name, Role role, String password, String phoneNumber, String officeNumber) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.officeNumber = officeNumber;
    }
}
