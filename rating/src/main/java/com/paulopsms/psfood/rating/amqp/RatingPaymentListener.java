package com.paulopsms.psfood.rating.amqp;

import com.paulopsms.psfood.rating.dto.PaymentDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RatingPaymentListener {

    @RabbitListener(queues = "payment.rating-details")
    public void onMessage(@Payload PaymentDto payment) {

        System.out.println(payment.id());
        System.out.println(payment.number());

        if (payment.number().equals("0001"))
            throw new RuntimeException("could not process message.");

        String message = """
                Rating Service!
                Payment info: %s
                Order number: %s
                value R$: %s
                Status: %s
                """.formatted(payment.id(), payment.orderId(), payment.paymentValue(), payment.status());

        System.out.println("Message received: " + message);
    }
}
