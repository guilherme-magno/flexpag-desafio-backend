package com.flexpag.paymentscheduler.domain;

import com.flexpag.paymentscheduler.PaymentSchedulerApplication;
import com.flexpag.paymentscheduler.enums.StatusPayment;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TB_PAYMENT_SCHEDULER")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "amount_payment")
    private Double amount;

    @Column(name = "date_payment")
    private LocalDateTime date;

    @Column(name = "status_payment")
    @Enumerated(EnumType.STRING)
    private StatusPayment status;

    public Payment(){

    }

    public Payment(Long id, String description, Double amount, LocalDateTime date, StatusPayment status) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.status = StatusPayment.PENDING;
    }
}
