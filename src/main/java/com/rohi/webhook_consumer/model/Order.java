package com.rohi.webhook_consumer.model;

import java.sql.Timestamp;
import java.util.Comparator;

public class Order implements Comparator<Order> {

    private Integer id;
    private Long timestamp;
    private String deliveryLocation ;
    private static final String WAREHOUSE_LOCATION = "Guindy";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getWarehouseLocation() {
        return WAREHOUSE_LOCATION;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", deliveryLocation='" + deliveryLocation + '\'' +
                '}';
    }

    // Private constructor to enforce the use of the builder
    private Order(Integer id, Long timestamp, String deliveryLocation) {
        this.id = id;
        this.timestamp = timestamp;
        this.deliveryLocation = deliveryLocation;
    }

    @Override
    public int compare(Order order1, Order order2) {
        // Check proximity to the warehouse location in the English alphabet
//        System.out.println(order1.getDeliveryLocation());
//        System.out.println(order2.getDeliveryLocation());
        int proximity1 = Math.abs(order1.getDeliveryLocation().charAt(0) - WAREHOUSE_LOCATION.charAt(0));
        int proximity2 = Math.abs(order2.getDeliveryLocation().charAt(0) - WAREHOUSE_LOCATION.charAt(0));

        // Compare proximity
        if (proximity1 != proximity2) {
            int i= Integer.compare(proximity1, proximity2);
            return i;
        }

        // If the delivery locations are the same, compare ids
        return Integer.compare(order1.getId(), order2.getId());
    }


    // Builder interface
    public interface Builder {
        Builder id(Integer id);

        Builder timestamp(Long timestamp);

        Builder deliveryLocation(String deliveryLocation);

        Builder warehouseLocation(String warehouseLocation);

        Order build();
    }

    // Concrete implementation of the builder interface
    public static class BuilderImpl implements Builder {
        private Integer id;
        private Long timestamp;
        private String deliveryLocation;
        private String warehouseLocation;

        @Override
        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        @Override
        public Builder timestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        @Override
        public Builder deliveryLocation(String deliveryLocation) {
            this.deliveryLocation = deliveryLocation;
            return this;
        }

        @Override
        public Builder warehouseLocation(String warehouseLocation) {
            this.warehouseLocation = warehouseLocation;
            return this;
        }

        @Override
        public Order build() {
            // Validate required fields
//            if (id == null) {
//                throw new IllegalStateException("Id is required");
//            }

            // You can add more validation if needed for other fields

            // Create and return the Order object
            return new Order(id, timestamp, deliveryLocation);
        }
    }

    // Static factory method to create a new builder
    /*
    Good Design Pattern: Abstraction: The interface defines a contract for building the object, hiding the
    implementation details. This abstraction allows clients to work with the builder without knowing
    its concrete type.Return an interface type not the concrete type, thereby hiding the
    implementation details from the client classes.
     */
    public static Builder builder() {
        return new BuilderImpl();
    }



}
