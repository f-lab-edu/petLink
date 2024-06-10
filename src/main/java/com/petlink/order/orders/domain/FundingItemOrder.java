package com.petlink.order.orders.domain;

import com.petlink.order.funding.item.domain.FundingItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import org.hibernate.annotations.Comment;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "funding_item_orders")
//@IdClass(FundingItemOrderKey.class)
public class FundingItemOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Comment("구매 수량")
    @Column(name = "quantity")
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;

    //Jpa가 저장하기 위해 별도록 관리하는 필드
    @ManyToOne
    @JoinColumn(name = "funding_item_id")
    private FundingItem fundingItem;

    public String getTitle() {
        return fundingItem.getTitle();
    }
}
