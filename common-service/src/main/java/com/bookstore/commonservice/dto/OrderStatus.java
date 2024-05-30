package com.bookstore.commonservice.dto;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED("created"),
    PROCESSING("processing"),
    SUCCESS("success"),
    FAILED("failed");

    private final String status;

    OrderStatus(String value) {
        this.status = value;
    }

}
