package com.bookstore.commonservice.dto;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED("created"),
    PROCESSING("processing"),
    INVENTORY_UPDATED("inventory_updated"),
    SUCCESS("success"),
    CANCELLED("cancelled");

    private final String status;

    OrderStatus(String value) {
        this.status = value;
    }

}
