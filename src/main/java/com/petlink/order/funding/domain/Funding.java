package com.petlink.order.funding.domain;

import com.petlink.common.domain.base.BaseEntity;
import com.petlink.order.orders.domain.Orders;
import com.petlink.user.manager.domain.Manager;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "funding")
public class Funding extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "id")
    private Long id;

    @Comment("펀딩 담당 매니저")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

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

    @OneToMany(mappedBy = "funding"
            , cascade = CascadeType.ALL // member가 삭제되면 orders도 삭제된다.
            , orphanRemoval = true      // member가 삭제되면 orders의 member를 null로 변경한다.
            , fetch = FetchType.LAZY    // member를 조회할 때 orders는 조회하지 않는다.
    )
    @Builder.Default
    private List<Orders> ordersList = new ArrayList<>();

    @Override
    public String toString() {
        return "Funding{" + "id=" + id + ", title='" + title + '\'' + ", miniTitle='" + miniTitle + '\'' +
                ", content='" + content + '\'' +
                ", state=" + state +
                ", category=" + category +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", targetDonation=" + targetDonation +
                ", successDonation=" + successDonation +
                '}';
    }


}
