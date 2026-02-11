package com.paulopsms.psfood.payment.mapper;

import com.paulopsms.psfood.payment.dto.PaymentDto;
import com.paulopsms.psfood.payment.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentDto mapToRecord(Payment payment) {
        return new PaymentDto(payment.getId(), payment.getPaymentValue(), payment.getName(), payment.getNumber(), payment.getExpiration(),
                payment.getCode(), payment.getStatus(), payment.getOrderId(), payment.getPaymentMethodId());
    }

    public Payment mapToEntity(PaymentDto paymentDto) {
        return new Payment(paymentDto.id(), paymentDto.paymentValue(), paymentDto.name(), paymentDto.number(), paymentDto.expiration(),
                paymentDto.code(), paymentDto.status(), paymentDto.orderId(), paymentDto.paymentMethodId());
    }
}
