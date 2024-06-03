package com.bookstore.commonservice.dto;

import lombok.Data;

@Data
public class Order {
    private Long orderId;
    private Long userId;
    private Long productId;

}
