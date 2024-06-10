package app.client.domain;

import app.client.domain.constant.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "email", "name"})
@Getter
@Entity
@Table(name = "client_users")
public class ClientUser {

    @Comment("사용자 이메일")
    @Column(unique = true, nullable = false)
    protected String email;
    @Comment("사용자 이름(닉네임)")
    @Column(name = "name", nullable = false)
    protected String name;
    @Column
    @Enumerated(EnumType.STRING)
    protected UserRole role;
    @Id
    @Comment("사용자 식별자")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private HomeAddress homeAddress;

    @Builder
    public ClientUser(Long id, String email, String name, UserRole role, HomeAddress homeAddress) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role == null ? UserRole.USER : role;
        this.homeAddress = homeAddress;
    }
}
