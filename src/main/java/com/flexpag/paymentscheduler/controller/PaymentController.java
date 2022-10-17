package com.flexpag.paymentscheduler.controller;

import com.flexpag.paymentscheduler.domain.Payment;
import com.flexpag.paymentscheduler.enums.StatusPayment;
import com.flexpag.paymentscheduler.service.PaymentService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/payments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController {

    final private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<Page<Payment>> getAllPayments(@PageableDefault(
                                                        page = 0,
                                                        size = 10,
                                                        sort = "id",
                                                        direction = Sort.Direction.ASC)
                                                        Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getPaymentById(@PathVariable("id") Long id) {
        Optional<Payment> paymentOptional = paymentService.findById(id);

        if (!paymentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID de pagamento não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(paymentOptional.get());
    }

    @GetMapping(value = "/status/{id}")
    public ResponseEntity<StatusPayment> getByStatus(@PathVariable Long id) {
        StatusPayment status = paymentService.findByStatus(id);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Object> createPayment(@RequestBody Payment newPayment) {
        Payment payment = paymentService.create(newPayment);

        if (newPayment.getAmount() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O valor deve ser maior que R$ 0.0");
        }

        if (newPayment.getDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data não pode ser inferior a data atual");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.create(payment));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Payment> executePayment(@PathVariable("id") Long id) {
        Payment paymentOK = paymentService.execute(id);

        return new ResponseEntity<>(paymentOK, HttpStatus.OK);
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updatePayment(@PathVariable(value = "id") Long id,
                                                @RequestBody Payment paymentUpdated) {
        Optional<Payment> paymentOptional = paymentService.findById(id);

        if (!paymentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID de pagamento não encontrado");
        }
        if (!paymentOptional.get().getStatus().equals(StatusPayment.PENDING)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Não é permitido ATUALIZAR ou EXCLUIR pagamentos com status PAID");
        }

        var paymentModel = new Payment();
        BeanUtils.copyProperties(paymentUpdated, paymentModel);
        paymentModel.setId(paymentOptional.get().getId());
        paymentModel.setDate(paymentOptional.get().getDate());
        paymentModel.setAmount(paymentOptional.get().getAmount());
        paymentModel.setDescription(paymentOptional.get().getDescription());

        return ResponseEntity.status(HttpStatus.OK).body(paymentService.update(paymentModel));
    }

}
