package com.restapi.repository;

import com.restapi.entity.Payment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepo extends JpaRepository<Payment,Long> {

    Payment findByTransactionId(String transactionId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Payment p WHERE p.transactionId = ?1")
    void deletePaymentByTransactionId(String transactionId);

}
