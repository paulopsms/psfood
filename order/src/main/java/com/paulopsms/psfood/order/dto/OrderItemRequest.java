package com.paulopsms.psfood.order.dto;

import com.paulopsms.psfood.order.model.Order;

public record OrderItemRequest(Integer quantity, String description, Order order) {
}
