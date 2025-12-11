package it.paofos.msscssm.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.oxo42.stateless4j.StateMachine;

import it.paofos.msscssm.domain.PaymentEvent;
import it.paofos.msscssm.domain.PaymentState;

@SpringBootTest
class StateMachineFactoryTest {

	@Autowired
	StateMachineFactory factory;
	
	@Test
	void testNewStateMachine() {
		StateMachine<PaymentState, PaymentEvent> sm = factory.create();
		
		System.out.println(sm.getState().toString());
		
		sm.fire(PaymentEvent.PRE_AUTHORIZE);

		System.out.println(sm.getState().toString());
		
		sm.fire(PaymentEvent.PRE_AUTH_APPROVED);

		System.out.println(sm.getState().toString());
		
		sm.fire(PaymentEvent.PRE_AUTH_DECLINED);

		System.out.println(sm.getState().toString());
	}

}
