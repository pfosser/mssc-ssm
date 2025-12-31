package it.paofos.msscssm.service;

import org.springframework.stereotype.Component;

import com.github.oxo42.stateless4j.delegates.Trace;

import it.paofos.msscssm.domain.PaymentEvent;
import it.paofos.msscssm.domain.PaymentState;
import it.paofos.msscssm.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PaymentStateChangeListener implements Trace<PaymentState, PaymentEvent> {
	
	PaymentRepository paymentRepository;

	@Override
	public void trigger(PaymentEvent trigger) {
	}

	@Override
	public void transition(PaymentEvent trigger, PaymentState source, PaymentState destination) {
		
	}

}
