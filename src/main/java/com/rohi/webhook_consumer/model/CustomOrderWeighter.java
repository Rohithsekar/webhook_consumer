package com.rohi.webhook_consumer.model;


import com.rohi.webhook_consumer.dto.OrderDTO;

import java.util.Comparator;

public class CustomOrderWeighter implements Comparator<OrderDTO> {

    private String WAREHOUSE_LOCATION;
    public CustomOrderWeighter() {
    }

    public CustomOrderWeighter(String WAREHOUSE_LOCATION) {
        this.WAREHOUSE_LOCATION = WAREHOUSE_LOCATION;
    }

    public String getWAREHOUSE_LOCATION() {
        return WAREHOUSE_LOCATION;
    }

    public void setWAREHOUSE_LOCATION(String WAREHOUSE_LOCATION) {
        this.WAREHOUSE_LOCATION = WAREHOUSE_LOCATION;
    }

    @Override
    public String toString() {
        return "CustomOrderWeighter{" +
                "WAREHOUSE_LOCATION='" + WAREHOUSE_LOCATION + '\'' +
                '}';
    }

    @Override
    public int compare(OrderDTO orderDTO1, OrderDTO orderDTO2) {

        int proximity1 = Math.abs(orderDTO1.getDeliveryLocation().charAt(0) - WAREHOUSE_LOCATION.charAt(0));
        int proximity2 = Math.abs(orderDTO2.getDeliveryLocation().charAt(0) - WAREHOUSE_LOCATION.charAt(0));

        // Compare proximity
        if (proximity1 != proximity2) {
            int i= Integer.compare(proximity1, proximity2);
            return i;
        }

        // If the delivery locations are the same, compare ids
        return Integer.compare(orderDTO1.getId(), orderDTO2.getId());
    }
}

