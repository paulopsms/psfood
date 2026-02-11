package com.paulopsms.psfood.order.controller;

import com.paulopsms.psfood.order.dto.OrderRequest;
import com.paulopsms.psfood.order.dto.OrderResponse;
import com.paulopsms.psfood.order.dto.StatusUpdateRequest;
import com.paulopsms.psfood.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        OrderResponse newOrder = this.orderService.createNewOrder(orderRequest);

        return ResponseEntity.ok(newOrder);
    }

    @PutMapping("{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable("id") Long id, @RequestBody @Valid StatusUpdateRequest statusUpdateRequest) {
        OrderResponse updatedOrder = this.orderService.updateStatus(id, statusUpdateRequest);

        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> listOrders() {
        List<OrderResponse> orders = this.orderService.listAllOrders();

        return ResponseEntity.ok(orders);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable("id") Long id) {
        OrderResponse order = this.orderService.getOrderById(id);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/port")
    public String getPort(@Value("${local.server.port}") String port) {
        return String.format("Executing instance on port %s", port);
    }
}
