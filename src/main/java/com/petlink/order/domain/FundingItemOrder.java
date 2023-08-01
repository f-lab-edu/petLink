package com.petlink.order.domain;

import com.petlink.funding.item.domain.FundingItem;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "funding_item_orders")
@IdClass(FundingItemOrderKey.class)
public class FundingItemOrder {
    @Id
    @Column(name = "orders_id")
    private Long ordersId;

    @Id
    @Column(name = "funding_item_id")
    private Long fundingItemId;

    @Column(name = "quantity")
    private Long quantity;

    //Jpa가 저장하기 위해 별도록 관리하는 필드
    //insertable는 id값을 insert할 때 포함시킬지 여부 (수정하면 안된다)
    //updatable은 id값을 update할 때 포함시킬지 여부 (수정하면 안된다)
    @ManyToOne
    @JoinColumn(name = "orders_id", insertable = false, updatable = false)
    private Orders orders;

    //Jpa가 저장하기 위해 별도록 관리하는 필드
    @ManyToOne
    @JoinColumn(name = "funding_item_id", insertable = false, updatable = false)
    private FundingItem fundingItem;
}
