package it.paofos.msscssm.service;

import org.springframework.stereotype.Service;

import it.paofos.msscssm.config.StateMachineFactory;
import it.paofos.msscssm.domain.Payment;
import it.paofos.msscssm.domain.PaymentState;
import it.paofos.msscssm.domain.PaymentStateMachine;
import it.paofos.msscssm.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

	public static final String PAYMENT_ID_HEADER = "payment_id";

	private final PaymentRepository paymentRepository;

	private final StateMachineFactory stateMachineFactory;

	@Override
	public Payment newPayment(Payment payment) {
		payment.setState(PaymentState.NEW);
		return paymentRepository.save(payment);
	}

	@Transactional
	@Override
	public PaymentStateMachine preAuth(Long paymentId) {
		PaymentStateMachine sm = build(paymentId);
		
		sm.preAuthorize();

		return sm;
	}

	@Transactional
	@Override
	public PaymentStateMachine authorizePayment(Long paymentId) {
		PaymentStateMachine sm = build(paymentId);
		
		sm.authorizePayment();

		return sm;
	}

	private PaymentStateMachine build(Long paymentId) {
		Payment payment = paymentRepository.getReferenceById(paymentId);

		PaymentStateMachine sm = stateMachineFactory.getStateMachine(paymentId, payment.getState());

		return sm;
	}

}
