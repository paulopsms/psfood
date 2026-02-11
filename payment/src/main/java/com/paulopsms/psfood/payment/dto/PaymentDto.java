package com.paulopsms.psfood.payment.dto;

import com.paulopsms.psfood.payment.model.Status;

import java.math.BigDecimal;

public record PaymentDto(Long id, BigDecimal paymentValue, String name, String number, String expiration, String code,
                         Status status, Long orderId, Long paymentMethodId) {
}
