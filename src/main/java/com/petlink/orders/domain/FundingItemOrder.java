package com.petlink.orders.domain;

import com.petlink.funding.item.domain.FundingItem;
import jakarta.persistence.*;
import lombok.*;
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
