package com.paulopsms.psfood.order.mapper;

import com.paulopsms.psfood.order.dto.OrderItemRequest;
import com.paulopsms.psfood.order.dto.OrderItemResponse;
import com.paulopsms.psfood.order.model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItemResponse mapToRecord(OrderItem item) {
        return new OrderItemResponse(item.getId(), item.getQuantity(), item.getDescription());
    }

    public OrderItem mapToEntity(OrderItemRequest orderItemRequest) {
        return new OrderItem(orderItemRequest.quantity(), orderItemRequest.description(), orderItemRequest.order());
    }
}
