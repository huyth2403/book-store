package com.bookstore.orderservice.service;

import com.bookstore.commonservice.dto.OrderEvent;
import com.bookstore.commonservice.dto.OrderStatus;
import com.bookstore.orderservice.entity.Order;
import com.bookstore.orderservice.entity.OrderDetail;
import com.bookstore.orderservice.repository.OrderDetailRepository;
import com.bookstore.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    public String handleOrder(Order order) {
        try {
            order = orderRepository.save(order);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getOrderId());
            orderDetail.setStatus(OrderStatus.CREATED.getStatus());
            orderDetail = orderDetailRepository.save(orderDetail);
            kafkaTemplate.send("order-topic", this.toOrderEvent(OrderStatus.CREATED, order, orderDetail));
            return OrderStatus.CREATED.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public OrderEvent toOrderEvent(OrderStatus orderStatus, Order order, OrderDetail orderDetail) {
        com.bookstore.commonservice.dto.Order orderDto = objectMapper.convertValue(order, com.bookstore.commonservice.dto.Order.class);
        com.bookstore.commonservice.dto.OrderDetail orderDetailDto = objectMapper.convertValue(orderDetail, com.bookstore.commonservice.dto.OrderDetail.class);
        return new OrderEvent(orderStatus, orderDto, orderDetailDto);
    }
}
