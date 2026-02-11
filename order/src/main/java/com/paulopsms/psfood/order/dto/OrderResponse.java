package com.paulopsms.psfood.order.dto;

import com.paulopsms.psfood.order.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(Long id, LocalDateTime dateTime, Status status, List<OrderItemResponse> items) {
}
