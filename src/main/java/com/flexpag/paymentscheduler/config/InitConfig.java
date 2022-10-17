package com.flexpag.paymentscheduler.config;

import com.flexpag.paymentscheduler.domain.Payment;
import com.flexpag.paymentscheduler.enums.StatusPayment;
import com.flexpag.paymentscheduler.repository.PaymentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class InitConfig implements CommandLineRunner {

    final PaymentRepository paymentRepository;

    public InitConfig(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Payment paymentInit = new Payment(1L, "conta detran pe", 310.9,LocalDateTime.parse("2022-12-13T00:00"),StatusPayment.PENDING);
        Payment paymentInit2 = new Payment(2L, "curso de ingles", 125.3,LocalDateTime.parse("2022-11-15T00:00"),StatusPayment.PENDING);
        Payment paymentInit3 = new Payment(3L, "cartão de credito", 456.9,LocalDateTime.parse("2023-01-13T00:00"),StatusPayment.PENDING);
        Payment paymentInit4 = new Payment(4L, "conta de luz", 200.9,LocalDateTime.parse("2022-12-10T00:00"),StatusPayment.PENDING);
        Payment paymentInit5 = new Payment(5L, "parcela notebook", 3000.0,LocalDateTime.parse("2022-10-20T00:00"),StatusPayment.PENDING);
        Payment paymentInit6 = new Payment(6L, "gasolina", 50.0,LocalDateTime.parse("2023-02-02T00:00"),StatusPayment.PENDING);
        Payment paymentInit7 = new Payment(7L, "seguro moto", 318.9,LocalDateTime.parse("2022-11-21T00:00"),StatusPayment.PENDING);


        paymentRepository.saveAll(Arrays.asList(paymentInit, paymentInit2, paymentInit3, paymentInit4, paymentInit5, paymentInit6, paymentInit7));

    }
}
