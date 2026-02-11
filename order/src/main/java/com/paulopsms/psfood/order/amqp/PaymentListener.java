package com.paulopsms.psfood.order.amqp;

import com.paulopsms.psfood.order.dto.PaymentDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    @RabbitListener(queues = "payment.order-details")
    public void onMessage(@Payload PaymentDto payment) {
        String message = """
                Payment info: %s
                Order number: %s
                value R$: %s
                Status: %s
                """.formatted(payment.id(), payment.orderId(), payment.paymentValue(), payment.status());

        System.out.println("Message received: " + message);
    }
}
