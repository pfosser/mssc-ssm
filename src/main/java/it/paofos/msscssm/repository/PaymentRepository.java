package it.paofos.msscssm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.paofos.msscssm.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
