package com.paulopsms.psfood.order.service;

import com.paulopsms.psfood.order.dto.OrderRequest;
import com.paulopsms.psfood.order.dto.OrderResponse;
import com.paulopsms.psfood.order.dto.StatusUpdateRequest;
import com.paulopsms.psfood.order.mapper.OrderMapper;
import com.paulopsms.psfood.order.model.Order;
import com.paulopsms.psfood.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderResponse> listAllOrders() {
        return this.orderRepository.findAll().stream()
                .map(this.orderMapper::mapToRecord)
                .toList();
    }

    public OrderResponse createNewOrder(OrderRequest orderRequest) {
        Order order = this.orderMapper.mapToEntity(orderRequest);

        order.getItems().forEach(item -> item.setOrder(order));

        Order savedOrder = this.orderRepository.save(order);

        return this.orderMapper.mapToRecord(savedOrder);
    }

    public OrderResponse updateStatus(Long id, StatusUpdateRequest statusUpdateRequest) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found."));

        order.setStatus(statusUpdateRequest.status());

        this.orderRepository.save(order);

        return this.orderMapper.mapToRecord(order);
    }

    public OrderResponse getOrderById(Long id) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found."));

        return this.orderMapper.mapToRecord(order);
    }
}
