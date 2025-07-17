package com.restapi.repository;

import com.restapi.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment,Long> {

    Payment findByTransactionId(String transactionId);

}
