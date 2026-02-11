package com.paulopsms.psfood.rating.dto;

import java.math.BigDecimal;

public record PaymentDto(Long id, BigDecimal paymentValue, String name, String number, String expiration, String code,
                         PaymentStatus status, Long orderId, Long paymentMethodId) {
}
