package com.projetmgl870.microservices.order.service;

import com.projetmgl870.microservices.order.client.InventoryClient;
import com.projetmgl870.microservices.order.dto.OrderRequest;
import com.projetmgl870.microservices.order.dto.OrderResponse;
import com.projetmgl870.microservices.order.event.OrderPlacedEvent;
import com.projetmgl870.microservices.order.model.Order;
import com.projetmgl870.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    Logger log = LoggerFactory.getLogger(Order.class);


    public void placeOrder(OrderRequest orderRequest) {

        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (isProductInStock) {

            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());


            orderRepository.save(order);

            //send a message to kafka

            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            log.info("Start  - Sending OrderPacedEvent {}  to kafka topic order-placed", orderPlacedEvent);

            kafkaTemplate.send("order-placed", orderPlacedEvent);

            log.info("End  - Sending OrderPacedEvent {}  to kafka topic order-placed", orderPlacedEvent);

        } else {
            throw new RuntimeException("Product  with " + orderRequest.skuCode() + "is not in stock");


        }
    }



    public List<OrderResponse> getAllOrders() {
        log.info("Fetching all orders from the database");
        try {
            List<OrderResponse> orders = orderRepository.findAll()
                    .stream()
                    .map(order -> new OrderResponse(
                            order.getId(),
                            order.getOrderNumber(),
                            order.getSkuCode(),
                            order.getPrice(),
                            order.getQuantity()
                    ))
                    .toList();

            log.info("Successfully fetched {} orders", orders.size());
            return orders;
        } catch (Exception e) {
            log.error("Failed to fetch orders from the database", e);
            throw new RuntimeException("Error while fetching orders", e);
        }
    }

}