package com.rohi.webhook_consumer.model;

import java.util.Comparator;
import java.util.PriorityQueue;

public class OrderWeighterQueue extends PriorityQueue<Order> {

    public OrderWeighterQueue(Comparator<Order> comparator) {
        super(comparator);
    }

    @Override
    public boolean offer(Order order) {
        // Iterate through the existing elements in the priority queue
        // and insert the new element at the appropriate position
        for (Order currentorder : this) {
            System.out.println(this.toString());
            //This condition becomes true when the current ORDER object has more priority than the
            //Head element of the priority queue.
            if (comparator().compare(order, currentorder) < 0) {
                System.out.println(order.toString());
                // If the new student has higher priority, insert it before the existing student
                super.remove(currentorder);
                super.offer(order);
                super.offer(currentorder);
                return true;
            }
        }
        // If the new order has lowest priority, simply add it to the end
        return super.offer(order);
    }
}
