package com.bookstore.inventoryservice.service;

import com.bookstore.commonservice.dto.OrderDetail;
import com.bookstore.commonservice.dto.OrderEvent;
import com.bookstore.commonservice.dto.OrderStatus;
import com.bookstore.inventoryservice.entity.Inventory;
import com.bookstore.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private InventoryRepository inventoryRepository;

    @KafkaListener(topics = "order-topic", groupId = "inventory")
    public void handleOrderEvent(OrderEvent orderEvent) {
        if (OrderStatus.CREATED.equals(orderEvent.getStatus())) {
            Inventory inventory = inventoryRepository.findByProductId(orderEvent.getOrder().getProductId());
            OrderDetail orderDetail = orderEvent.getOrderDetail();
            if (inventory != null) {
                if (inventory.getQuantity() >= orderDetail.getQuantity()) {
                    inventory.setQuantity(inventory.getQuantity() - orderDetail.getQuantity());
                    inventoryRepository.save(inventory);
                    System.out.println("Inventory Updated");
                    orderEvent.setStatus(OrderStatus.INVENTORY_UPDATED);
                    kafkaTemplate.send("inventory-topic", orderEvent);
                } else {
                    this.onCancelled("sold out", orderEvent);
                }
            } else {
                this.onCancelled("product doest not exist", orderEvent);
            }
        }
    }

    public void onCancelled(String reason, OrderEvent orderEvent) {
        OrderDetail orderDetail = orderEvent.getOrderDetail();
        orderDetail.setStatus(OrderStatus.CANCELLED.getStatus());
        orderDetail.setReason(reason);
        kafkaTemplate.send("order-topic", orderEvent);
    }

}

