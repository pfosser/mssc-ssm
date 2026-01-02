package it.paofos.msscssm.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.paofos.msscssm.domain.Payment;
import it.paofos.msscssm.domain.PaymentStateMachine;
import it.paofos.msscssm.repository.PaymentRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
class PaymentServiceImplTest {

	@Autowired
	PaymentService paymentService;

	@Autowired
	PaymentRepository paymentRepository;

	Payment payment;

	@BeforeEach
	void setUp() throws Exception {
		payment = Payment.builder().amount(new BigDecimal("12.99")).build();
	}

	@Transactional
	@Test
	void testPreAuth() {
		Payment savedPayment = paymentService.newPayment(payment);

		System.out.println("Should be NEW");
		System.out.println(savedPayment.getState());

		PaymentStateMachine sm = paymentService.preAuth(savedPayment.getId());

		Payment preAuthedPayment = paymentRepository.getReferenceById(savedPayment.getId());
		
		System.out.println("Should be PRE_AUTH or PRE_AUTH_ERROR");
		System.out.println(sm.getState());

		System.out.println(preAuthedPayment);
	}

}
