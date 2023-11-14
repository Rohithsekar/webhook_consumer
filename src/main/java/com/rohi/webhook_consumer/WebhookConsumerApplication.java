package com.rohi.webhook_consumer;

import com.rohi.webhook_consumer.model.Order;
import com.rohi.webhook_consumer.model.OrderWeighterQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

@SpringBootApplication
@RestController
@RequestMapping("/webhook")
public class WebhookConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebhookConsumerApplication.class, args);
	}

	@PostMapping
	@RequestMapping("/sort")
	public ResponseEntity<Collection<Order>> sortOrders(
			@RequestBody @Valid List<Order> orders,
			@RequestHeader("Authorization") String authorizationHeader) {

		// Verify the Authorization header
		if (!"GRANT".equals(authorizationHeader)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String deliveryLocations[] = {"Chennai", "Delhi", "Hyderabad", "Florida", "Zurich", "Hamburg"};
		// Process the payloadx
		Queue<Order> orderQueue = new OrderWeighterQueue(Order.builder().build());
		for (Order order : orders) {
			orderQueue.offer(order);
		}

		System.out.println(Arrays.toString(orderQueue.toArray()));

		return ResponseEntity.status(HttpStatus.OK).body(orderQueue);
	}

}
