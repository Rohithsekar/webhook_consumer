package com.rohi.webhook_consumer.dto;

import javax.validation.constraints.NotNull;

public class UnsortedOrder {

    @NotNull(message = "deliveryLocation cannot be null")
    private String deliveryLocation;

    @NotNull(message = "warehouseLocation cannot be null")
    private String warehouseLocation;
}
