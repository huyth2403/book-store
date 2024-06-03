package com.bookstore.commonservice.dto;

import lombok.Data;

@Data
public class OrderDetail {
    private Long orderDetailId;
    private String status;
    private String reason;
    private Long orderId;
    private Long quantity;

}
