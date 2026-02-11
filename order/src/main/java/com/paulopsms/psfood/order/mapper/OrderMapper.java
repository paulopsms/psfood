package com.paulopsms.psfood.order.mapper;

import com.paulopsms.psfood.order.dto.OrderItemResponse;
import com.paulopsms.psfood.order.dto.OrderRequest;
import com.paulopsms.psfood.order.dto.OrderResponse;
import com.paulopsms.psfood.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    @Autowired
    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    public OrderResponse mapToRecord(Order order) {
        List<OrderItemResponse> items = order.getItems()
                .stream()
                .map(this.orderItemMapper::mapToRecord)
                .toList();

        return new OrderResponse(order.getId(), order.getDateTime(), order.getStatus(), items);
    }

    public Order mapToEntity(OrderRequest orderResponse) {
        return new Order(orderResponse.items());
    }
}
