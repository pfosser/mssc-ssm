package it.paofos.msscssm.config;

import org.springframework.stereotype.Component;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;

import it.paofos.msscssm.domain.PaymentEvent;
import it.paofos.msscssm.domain.PaymentState;

@Component
public class StateMachineFactory {

	public StateMachine<PaymentState, PaymentEvent> create() {

		StateMachineConfig<PaymentState, PaymentEvent> paymentConfig = new StateMachineConfig<>();

		paymentConfig.configure(PaymentState.NEW) //
				.ignore(PaymentEvent.PRE_AUTHORIZE) //
				.permit(PaymentEvent.PRE_AUTH_APPROVED, PaymentState.PRE_AUTH) //
				.permit(PaymentEvent.PRE_AUTH_DECLINED, PaymentState.PRE_AUTH_ERROR);

		paymentConfig.configure(PaymentState.PRE_AUTH) //
				.ignore(PaymentEvent.PRE_AUTH_DECLINED);

		return new StateMachine<PaymentState, PaymentEvent>(PaymentState.NEW, paymentConfig);
	}
}
