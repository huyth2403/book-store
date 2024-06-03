package com.bookstore.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_detail")
@Data
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;
    private String status;
    private String reason;
    private Long orderId;
    private Long quantity;

}
