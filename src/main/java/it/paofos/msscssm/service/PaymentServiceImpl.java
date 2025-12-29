package it.paofos.msscssm.service;

import org.springframework.stereotype.Service;

import it.paofos.msscssm.config.StateMachineFactory;
import it.paofos.msscssm.domain.Payment;
import it.paofos.msscssm.domain.PaymentState;
import it.paofos.msscssm.domain.PaymentStateMachine;
import it.paofos.msscssm.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;
	
	private final StateMachineFactory stateMachineFactory;

	@Override
	public Payment newPayment(Payment payment) {
		payment.setState(PaymentState.NEW);
		return paymentRepository.save(payment);
	}

	@Override
	public PaymentStateMachine preAuth(Long paymentId) {
		PaymentStateMachine sm = build(paymentId);
		return null;
	}

	@Override
	public PaymentStateMachine authorizePayment(Long paymentId) {
		PaymentStateMachine sm = build(paymentId);
		return null;
	}

	@Override
	public PaymentStateMachine declineAuth(Long paymentId) {
		PaymentStateMachine sm = build(paymentId);
		return null;
	}
	
	private PaymentStateMachine build(Long paymentId) {
		Payment payment = paymentRepository.getReferenceById(paymentId);
		
		PaymentStateMachine sm = stateMachineFactory.getStateMachine(paymentId, payment.getState());
		
		return sm;
	}

}
