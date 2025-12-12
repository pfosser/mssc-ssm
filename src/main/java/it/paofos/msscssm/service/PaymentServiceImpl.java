package it.paofos.msscssm.service;

import org.springframework.stereotype.Service;

import com.github.oxo42.stateless4j.StateMachine;

import it.paofos.msscssm.config.StateMachineFactory;
import it.paofos.msscssm.domain.Payment;
import it.paofos.msscssm.domain.PaymentEvent;
import it.paofos.msscssm.domain.PaymentState;
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
	public StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
