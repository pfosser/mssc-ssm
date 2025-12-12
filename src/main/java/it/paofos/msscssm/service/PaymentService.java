package it.paofos.msscssm.service;

import com.github.oxo42.stateless4j.StateMachine;

import it.paofos.msscssm.domain.Payment;
import it.paofos.msscssm.domain.PaymentEvent;
import it.paofos.msscssm.domain.PaymentState;

public interface PaymentService {

	Payment newPayment(Payment payment);
	
	StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId);
	
	StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymentId);
	
	StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymentId);
}
