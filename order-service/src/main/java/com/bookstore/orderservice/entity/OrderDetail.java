package com.bookstore.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_detail")
@Data
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailId;
    private String status;
    private String reason;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
