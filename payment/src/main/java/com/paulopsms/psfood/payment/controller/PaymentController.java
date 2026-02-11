package com.paulopsms.psfood.payment.controller;

import com.paulopsms.psfood.payment.dto.PaymentDto;
import com.paulopsms.psfood.payment.service.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public Page<PaymentDto> listPayments(@PageableDefault() Pageable pageable) {
        return this.paymentService.listAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable @NotNull Long id) {
        PaymentDto paymentDto = this.paymentService.findById(id);

        return ResponseEntity.ok(paymentDto);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody @Valid PaymentDto paymentDto, UriComponentsBuilder uriBuilder) {
        PaymentDto payment = this.paymentService.createPayment(paymentDto);
        URI address = uriBuilder.path("/payments/{id}").buildAndExpand(payment.id()).toUri();

//        Message message = new Message(("A payment was created with id " + payment.id()).getBytes());
        rabbitTemplate.convertAndSend("payment.ex", "", payment);

        return ResponseEntity.created(address).body(payment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable @NotNull Long id, @RequestBody @Valid PaymentDto paymentDto) {
        PaymentDto updatedPayment = this.paymentService.updatePayment(id, paymentDto);

        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDto> removePayment(@PathVariable @NotNull Long id) {
        this.paymentService.removePayment(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @CircuitBreaker(name = "confirmOrder", fallbackMethod = "authorizeNotIntegratedPayment")
    public ResponseEntity<PaymentDto> confirmPayment(@PathVariable @NotNull Long id) {
        PaymentDto confirmedPayment = this.paymentService.confirmPayment(id, false);

        return ResponseEntity.ok(confirmedPayment);
    }

    public ResponseEntity<PaymentDto> authorizeNotIntegratedPayment(Long id, Exception e) {
        PaymentDto confirmedPayment = this.paymentService.confirmPayment(id, true);

        return ResponseEntity.ok(confirmedPayment);
    }
}
