package com.petlink.funding.domain;

import com.petlink.common.domain.base.BaseEntity;
import com.petlink.manager.domain.Manager;
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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "mini_title")
    private String miniTitle;

    @Column(nullable = false, name = "content", columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "state")
    private FundingState state;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "category")
    private FundingCategory category;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Long targetDonation;

    @Column(nullable = false)
    private Long successDonation;

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
