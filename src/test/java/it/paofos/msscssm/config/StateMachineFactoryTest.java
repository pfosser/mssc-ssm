package it.paofos.msscssm.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.paofos.msscssm.domain.PaymentStateMachine;

@SpringBootTest
class StateMachineFactoryTest {

	@Autowired
	StateMachineFactory factory;
	
	@Test
	void testNewStateMachine() {
		PaymentStateMachine sm = factory.create();
		
		System.out.println(sm.getState().toString());
		
		sm.preAuthorize();

		System.out.println(sm.getState().toString());
		
		sm.preAuthorize();

		System.out.println(sm.getState().toString());
		
		sm.declineAuth();

		System.out.println(sm.getState().toString());
	}

}
