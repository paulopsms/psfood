package com.paulopsms.psfood.order.dto;

import com.paulopsms.psfood.order.model.OrderItem;
import com.paulopsms.psfood.order.model.Status;

import java.util.List;

public record StatusUpdateRequest(Status status) {
}
