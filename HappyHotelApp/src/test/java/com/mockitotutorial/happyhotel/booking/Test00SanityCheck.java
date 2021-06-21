package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;

class Test00SanityCheck {

	//golden rule: we can mock all the other classes the class we are testing has dependencies on, 
	//but we should NEVER mock the class we are testing.  Then we'd be testing the mock...
	
	@Test
	void test() {
		// should pass
	}

}
