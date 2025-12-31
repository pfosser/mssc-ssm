package it.paofos.msscssm.service;

import it.paofos.msscssm.domain.Payment;
import it.paofos.msscssm.domain.PaymentStateMachine;

public interface PaymentService {

	Payment newPayment(Payment payment);
	
	PaymentStateMachine preAuth(Long paymentId);
	
	PaymentStateMachine authorizePayment(Long paymentId);
	
	PaymentStateMachine declineAuth(Long paymentId);
}
