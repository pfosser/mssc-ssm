package it.paofos.msscssm.domain;

import org.springframework.messaging.Message;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentStateMachine extends StateMachine<PaymentState, PaymentEvent> {
	
	private Long id;

	public PaymentStateMachine(Long id, PaymentState initialState, StateMachineConfig<PaymentState, PaymentEvent> config) {
		this(initialState, config);
		this.id = id;
	}
	public PaymentStateMachine(PaymentState initialState, StateMachineConfig<PaymentState, PaymentEvent> config) {
		super(initialState, config);
	}

	public void fire(Message<PaymentEvent> msg) {
		
	}

}
