package com.rohi.webhook_consumer.model;

import com.rohi.webhook_consumer.dto.OrderDTO;

import java.util.*;


public class OrderWeighterList {

    private LinkedList<Order> orderList;
    private Comparator<Order> orderComparator;
    private Comparator<OrderDTO> orderDTOComparator;
    private LinkedList<OrderDTO> orderDTOLinkedList;

    public OrderWeighterList(Comparator<Order> comparator) {
        this.orderList = new LinkedList<>();
        this.orderComparator = comparator;
    }

    public OrderWeighterList(Comparator<OrderDTO> orderDTOComparator, LinkedList<OrderDTO> orderDTOLinkedList){
        this.orderDTOComparator = orderDTOComparator;
        this.orderDTOLinkedList = orderDTOLinkedList;
    }

    public Comparator<OrderDTO> getOrderDTOComparator() {
        return orderDTOComparator;
    }

    public void setOrderDTOComparator(Comparator<OrderDTO> orderDTOComparator) {
        this.orderDTOComparator = orderDTOComparator;
    }

    public LinkedList<OrderDTO> getOrderDTOLinkedList() {
        return orderDTOLinkedList;
    }

    public void setOrderDTOLinkedList(LinkedList<OrderDTO> orderDTOLinkedList) {
        this.orderDTOLinkedList = orderDTOLinkedList;
    }

    public LinkedList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(LinkedList<Order> orderList) {
        this.orderList = orderList;
    }

    public Comparator<Order> getOrderComparator() {
        return orderComparator;
    }

    public void setComparator(Comparator<Order> comparator) {
        this.orderComparator = comparator;
    }

    /**
     *
     * @param order
     * @return boolean
     *
     * Here's how Collections.binarySearch works:
     *
     * If the list contains the search key, it returns the index of the key. This index is the position of
     * the key in the list.
     *
     * If the list does not contain the search key, it returns a negative value. The negative value is
     * calculated as -(insertion point) - 1. The insertion point is the index at which the key would be
     * inserted into the list in order to maintain the sorted order.
     *
     * Let's break down the code:
     *
     * int index = Collections.binarySearch(orderList, order, comparator);
     *
     * orderList: The list to be searched (in this case, LinkedList of Order objects).
     * order: The key to be searched for (the Order object you want to insert).
     * comparator: The comparator to determine the order of the elements.
     *
     * After this line, the variable index will contain the result of the binary search.
     *
     * Now, if index is non-negative(positive), it means that the Order object is already present in the list,
     * and index is the position of the existing Order object.
     *
     * If index is negative, it means the Order object is not present in the list, and you need to adjust
     * it using the formula:
     *
     * java
     * Copy code
     * if (index < 0) {
     *     index = -(index + 1);
     * }
     * This adjustment converts the negative index into the insertion point where the Order object should be added to maintain the sorted order of the list.
     *
     * In your weigh method, you then add the Order object at the adjusted index:
     *
     * java
     * Copy code
     * orderList.add(index, order);
     * This ensures that the Order objects are inserted at the correct position in the sorted list.
     */

    public boolean weighOrder(Order order) {
        // Use Collections.binarySearch to find the insertion point based on the custom comparator
        int index = Collections.binarySearch(orderList, order, orderComparator);

        // Adjust the index if the result is negative
        if (index < 0) {
            index = -(index + 1);
        }

        // Add the order at the correct position
        orderList.add(index, order);

        return true;
    }


    public boolean weighOrderDTO(OrderDTO orderDTO) {
        // Use Collections.binarySearch to find the insertion point based on the custom comparator
        int index = Collections.binarySearch(orderDTOLinkedList, orderDTO, orderDTOComparator);

        // Adjust the index if the result is negative
        if (index < 0) {
            index = -(index + 1);
        }

        // Add the order at the correct position
        orderDTOLinkedList.add(index, orderDTO);

        return true;
    }

    // Other methods as needed
}
