package com.paulopsms.psfood.payment.service;

import com.paulopsms.psfood.payment.client.OrderClient;
import com.paulopsms.psfood.payment.client.OrderStatus;
import com.paulopsms.psfood.payment.client.OrderStatusRequest;
import com.paulopsms.psfood.payment.dto.PaymentDto;
import com.paulopsms.psfood.payment.mapper.PaymentMapper;
import com.paulopsms.psfood.payment.model.Payment;
import com.paulopsms.psfood.payment.model.Status;
import com.paulopsms.psfood.payment.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private OrderClient orderClient;

    public Page<PaymentDto> listAll(Pageable pageable) {
        return this.paymentRepository.findAll(pageable)
                .map(payment -> this.paymentMapper.mapToRecord(payment));
    }

    public PaymentDto findById(Long id) {
        Payment payment = this.paymentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payment not found."));

        return this.paymentMapper.mapToRecord(payment);
    }

    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = this.paymentMapper.mapToEntity(paymentDto);
        payment.setStatus(Status.CREATED);

        this.paymentRepository.save(payment);

        return this.paymentMapper.mapToRecord(payment);
    }

    public PaymentDto updatePayment(Long id, PaymentDto paymentDto) {
        Payment payment = this.paymentMapper.mapToEntity(paymentDto);
        payment.setId(id);

        payment = this.paymentRepository.save(payment);

        return this.paymentMapper.mapToRecord(payment);
    }

    public void removePayment(Long id) {
        this.paymentRepository.deleteById(id);
    }


    public PaymentDto confirmPayment(Long id, Boolean isFallback) {
        Payment payment = this.paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found."));

        Status status = isFallback ? Status.CONFIRMED_NOT_INTEGRATED : Status.CONFIRMED;

        payment.setStatus(status);

        this.paymentRepository.save(payment);

        if (!isFallback) {
            OrderStatusRequest request = new OrderStatusRequest(OrderStatus.PAID);

            orderClient.updateStatus(payment.getOrderId(), request);
        }

        return this.paymentMapper.mapToRecord(payment);
    }
}
