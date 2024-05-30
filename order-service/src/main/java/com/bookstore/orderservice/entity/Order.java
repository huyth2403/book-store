package com.bookstore.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private Integer userId;
    private Integer quantity;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderDetail orderDetail;

}
