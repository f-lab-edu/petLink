package com.petlink.order.domain;

import com.petlink.order.exception.OrdersException;
import com.petlink.order.exception.OrdersExceptionCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PayMethod {
    CREDIT_CARD("신용카드"),
    DEBIT_CARD("데빗카드"),
    PAYPAL("페이팔"),
    BANK_TRANSFER("무통장입금");
    private final String description;

    public static PayMethod of(String description) {
        for (PayMethod payMethod : PayMethod.values()) {
            if (payMethod.getDescription().equals(description)) {
                return payMethod;
            }
        }
        throw new OrdersException(OrdersExceptionCode.PAY_METHOD_NOT_FOUND);
    }
}
