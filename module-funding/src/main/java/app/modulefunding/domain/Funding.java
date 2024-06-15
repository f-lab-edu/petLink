package app.modulefunding.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "funding")
public class Funding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "id")
    private Long id;

    @Comment("펀딩 담당 매니저")
    @Column(name = "manager_id")
    private Long managerId;

    @Comment("펀딩 제목")
    @Column(nullable = false, name = "title")
    private String title;

    @Comment("펀딩 소제목")
    @Column(nullable = false, name = "mini_title")
    private String miniTitle;

    @Comment("펀딩 내용")
    @Column(nullable = false, name = "content", columnDefinition = "TEXT")
    private String content;

    @Comment("펀딩 상태 : FundingState.class")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "state")
    private FundingState state;

    @Comment("펀딩 카테고리 : FundingCategory.class")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "category")
    private FundingCategory category;

    @Comment("펀딩 시작일")
    @Column(nullable = false)
    private LocalDateTime startDate;

    @Comment("펀딩 종료일")
    @Column(nullable = false)
    private LocalDateTime endDate;

    @Comment("펀딩 목표금액")
    @Column(nullable = false)
    private Long targetDonation;

    @Comment("펀딩 성공금액 : targetDonation의 80% 이상일 경우 성공")
    @Column(nullable = false)
    private Long successDonation;
}
