package com.flexpag.paymentscheduler.repository;

import com.flexpag.paymentscheduler.domain.Payment;
import com.flexpag.paymentscheduler.enums.StatusPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
