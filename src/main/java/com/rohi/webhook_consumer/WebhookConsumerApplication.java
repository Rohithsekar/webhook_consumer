package com.rohi.webhook_consumer;

import com.rohi.webhook_consumer.dto.OrderDTO;
import com.rohi.webhook_consumer.dto.StructuredOrder;
import com.rohi.webhook_consumer.model.Order;
import com.rohi.webhook_consumer.model.CustomOrderWeighter;
import com.rohi.webhook_consumer.model.OrderWeighterList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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

		// Process the payload
		Order comparator = Order.builder().build();
		OrderWeighterList orderWeighterList = new OrderWeighterList(comparator);
		for (Order order : orders) {
			orderWeighterList.weighOrder(order);
		}

		List<Order> sortedOrders = orderWeighterList.getOrderList();
		return ResponseEntity.status(HttpStatus.OK).body(sortedOrders);
	}

	@PostMapping
	@RequestMapping("/custom/sort")
	public ResponseEntity<StructuredOrder> sortOrdersWithCustomComparator(
			@RequestBody @Valid StructuredOrder orderRequest,
			@RequestHeader("Authorization") String authorizationHeader) {

		// Verify the Authorization header
		if (!"GRANT".equals(authorizationHeader)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		// Process the payload
		CustomOrderWeighter customOrderWeighter = new CustomOrderWeighter(orderRequest.getWarehouseLocation());
		OrderWeighterList orderWeighterList = new OrderWeighterList(customOrderWeighter, new LinkedList<>());

		OrderDTO[] orders = orderRequest.getDeliveryLocations();

		for (OrderDTO order : orders) {
			orderWeighterList.weighOrderDTO(order);
		}

		LinkedList<OrderDTO> orderDTOS = orderWeighterList.getOrderDTOLinkedList();
		int count = (int)orderWeighterList.getOrderDTOLinkedList().stream().count();
		OrderDTO[] sortedOrders = new OrderDTO[count];
		for(int i =0; i<count; i++){
			sortedOrders[i] = orderDTOS.get(i);
		}

		orderRequest.setDeliveryLocations(sortedOrders);
		return ResponseEntity.status(HttpStatus.OK).body(orderRequest);
	}

}
