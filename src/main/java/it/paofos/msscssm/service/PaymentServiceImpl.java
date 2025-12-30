package it.paofos.msscssm.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import it.paofos.msscssm.config.StateMachineFactory;
import it.paofos.msscssm.domain.Payment;
import it.paofos.msscssm.domain.PaymentEvent;
import it.paofos.msscssm.domain.PaymentState;
import it.paofos.msscssm.domain.PaymentStateMachine;
import it.paofos.msscssm.repository.PaymentRepository;
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

	@Override
	public PaymentStateMachine preAuth(Long paymentId) {
		PaymentStateMachine sm = build(paymentId);
		
		sendEvent(paymentId, sm, PaymentEvent.PRE_AUTHORIZE);

		return null;
	}

	@Override
	public PaymentStateMachine authorizePayment(Long paymentId) {
		PaymentStateMachine sm = build(paymentId);
		
		sendEvent(paymentId, sm, PaymentEvent.AUTH_APPROVED);

		return null;
	}

	@Override
	public PaymentStateMachine declineAuth(Long paymentId) {
		PaymentStateMachine sm = build(paymentId);
		
		sendEvent(paymentId, sm, PaymentEvent.AUTH_DECLINED);

		return null;
	}

	private void sendEvent(Long paymentId, PaymentStateMachine sm, PaymentEvent event) {
		Message<PaymentEvent> msg = MessageBuilder.withPayload(event) //
			.setHeader(PAYMENT_ID_HEADER, paymentId) //
			.build();
		
		sm.fire(msg);
	}

	private PaymentStateMachine build(Long paymentId) {
		Payment payment = paymentRepository.getReferenceById(paymentId);

		PaymentStateMachine sm = stateMachineFactory.getStateMachine(paymentId, payment.getState());

		return sm;
	}

}
