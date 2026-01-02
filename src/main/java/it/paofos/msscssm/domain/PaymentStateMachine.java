package it.paofos.msscssm.domain;

import com.github.oxo42.stateless4j.StateMachine;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentStateMachine {
	
	private final Long id;
	private final StateMachine<PaymentState, PaymentEvent> machine;

	public void preAuthorize() {
		machine.fire(PaymentEvent.PRE_AUTHORIZE);
	}

	public void authorizePayment() {
		machine.fire(PaymentEvent.AUTH_APPROVED);
	}

	public void declineAuth() {
		machine.fire(PaymentEvent.AUTH_DECLINED);
	}
	
	public PaymentState getState() {
		return machine.getState();
	}
}
