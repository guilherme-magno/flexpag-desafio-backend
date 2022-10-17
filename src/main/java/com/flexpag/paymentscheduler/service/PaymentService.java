package com.flexpag.paymentscheduler.service;

import com.flexpag.paymentscheduler.domain.Payment;
import com.flexpag.paymentscheduler.enums.StatusPayment;
import com.flexpag.paymentscheduler.repository.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    public Page<Payment> findAll(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    @Transactional
    public Payment create(Payment newPayment) {
        newPayment.setStatus(StatusPayment.PENDING);
        newPayment.setDate(newPayment.getDate());
        newPayment.setAmount(newPayment.getAmount());

        return paymentRepository.save(newPayment);
    }

    @Transactional
    public Payment execute(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        payment.get().setStatus(StatusPayment.PAID);
        payment.get().setDate(payment.get().getDate());

        return paymentRepository.save(payment.get());
    }

    public StatusPayment findByStatus(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        return payment.get().getStatus();
    }

//    @Transactional
//    public Payment update(Long id, Payment payment) {
//        Optional<Payment> p = paymentRepository.findById(id);
//        p.get().setStatus(payment.getStatus());
//        p.get().setAmount(payment.getAmount());
//        p.get().setDate(payment.getDate());
//
//        return paymentRepository.save(payment);
//    }

    @Transactional
    public Payment update(Payment payment){
        return paymentRepository.save(payment);
    }

}