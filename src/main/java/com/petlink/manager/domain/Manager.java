package com.petlink.manager.domain;

import com.petlink.common.domain.user.BaseUser;
import com.petlink.common.util.jwt.UserRole;
import com.petlink.funding.domain.Funding;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "manager")
public class Manager extends BaseUser {
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

    // 빌더 인스턴스 생성 메소드
    public static ManagerBuilder builder() {
        return new ManagerBuilder();
    }

    // 커스텀 빌더 정의
    public static class ManagerBuilder {
        // BaseUser 및 Manager 필드
        protected Long id;
        protected String email;
        protected String name;
        private String password;
        private String phoneNumber;
        private String officeNumber;

        // 필드 설정 메소드들
        public ManagerBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ManagerBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ManagerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ManagerBuilder password(String password) {
            this.password = password;
            return this;
        }

        public ManagerBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ManagerBuilder officeNumber(String officeNumber) {
            this.officeNumber = officeNumber;
            return this;
        }

        public Manager build() {
            Manager manager = new Manager();
            manager.id = this.id;
            manager.email = this.email;
            manager.name = this.name;
            manager.role = UserRole.MANAGER;
            manager.password = this.password;
            manager.phoneNumber = this.phoneNumber;
            manager.officeNumber = this.officeNumber;
            return manager;
        }
    }
}
