package it.paofos.msscssm.config;

import org.springframework.stereotype.Component;

import com.github.oxo42.stateless4j.StateConfiguration;
import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;

import it.paofos.msscssm.domain.Payment;
import it.paofos.msscssm.domain.PaymentEvent;
import it.paofos.msscssm.domain.PaymentState;
import it.paofos.msscssm.domain.PaymentStateMachine;
import it.paofos.msscssm.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class StateMachineFactory {

	private final PaymentRepository paymentRepository;

	public PaymentStateMachine create() {
		return internalCreate(null, PaymentState.NEW);
	}

	private PaymentStateMachine internalCreate(Long id, PaymentState initialState) {
		StateMachineConfig<PaymentState, PaymentEvent> paymentConfig = new StateMachineConfig<>();

		StateConfiguration<PaymentState, PaymentEvent> stateConfig = paymentConfig.configure(PaymentState.NEW) //
				.ignore(PaymentEvent.PRE_AUTHORIZE) //
				.permit(PaymentEvent.PRE_AUTH_APPROVED, PaymentState.PRE_AUTH) //
				.permit(PaymentEvent.PRE_AUTH_DECLINED, PaymentState.PRE_AUTH_ERROR);

		addTransitionLog(stateConfig);
		addPersistence(id, stateConfig);

		stateConfig = paymentConfig.configure(PaymentState.PRE_AUTH) //
				.ignore(PaymentEvent.PRE_AUTH_DECLINED);

		addTransitionLog(stateConfig);
		addPersistence(id, stateConfig);

		return new PaymentStateMachine(id, new StateMachine<PaymentState, PaymentEvent>(initialState, paymentConfig));
	}

	private StateConfiguration<PaymentState, PaymentEvent> addPersistence(Long id,
			StateConfiguration<PaymentState, PaymentEvent> config) {
		return config.onEntry((transition) -> {
			Payment payment = paymentRepository.getReferenceById(id);
			payment.setState(transition.getDestination());
			paymentRepository.save(payment);
		}); //
	}

	private StateConfiguration<PaymentState, PaymentEvent> addTransitionLog(
			StateConfiguration<PaymentState, PaymentEvent> config) {
		return config.onEntry((transition) -> log
				.info("State changed (from %s to %s)".formatted(transition.getSource(), transition.getDestination()))); //
	}

	public PaymentStateMachine getStateMachine(Long paymentId, PaymentState state) {
		return internalCreate(paymentId, state);
	}
}
