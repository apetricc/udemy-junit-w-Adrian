package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.*;
//import com.sun.tools.javac.util.List;
//golden rule: we can mock all the other classes the class we are testing has dependencies on, 
//but we should NEVER mock the class we are testing.  Then we'd be testing the mock...

@ExtendWith(MockitoExtension.class) // in junit4 you would use @RunWith, but w/ junit 5 we use @ExtendWith
class Test13StrictStubbing {
	// instead of using a setup() method, we can use Mockito annotations which is
	// the preferred method of working with mockito actually:

	@InjectMocks
	private BookingService bookingService;

	@Mock
	private PaymentService paymentServiceMock;
	@Mock
	private RoomService roomServiceMock;
	@Spy
	private BookingDAO bookingDAOMock;
	@Mock
	private MailSender mailSenderMock;
	@Mock
	private ArgumentCaptor<Double> doubleCaptor;
	// if we wanted an argument captor for another type of object, we just pass in
	// the "<>" like this:
	@Captor
	private ArgumentCaptor<BookingRequest> bookingRequestCaptor;

	// *******************************************************************************************************
	// the given, when, then pattern comes from the behavior driven development
	// principle, or "BDD" for short
	// although it doesn't really match the methods in Mockito, but there are
	// aliases we can use to make it match better to the "given, when, then" pattern 
	// *******************************************************************************************************
	
	@Test
	void should_InvokePayment_When_Prepaid() {
		// given
		// the 'prepaid' argument is true, so the paymentServiceMock should get called,
		// and we want to test that it was in fact called;
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
				LocalDate.of(2020, 01, 05), 2, false);
//		 if the 'prepaid' argument is 'false', paymentServiceMock will never get used--> 
//		 which makes this 'when, thenReturn' a case of "unnecessary stubbing"  
//		(stubbing: defining some behavior of a class with "when" in classic mockito, or with "given" in BDD style Mockito)
//		 when we use the "MockitoExtension.class" feature of Mockito, it enables "strict stubbing" 
//		 when(paymentServiceMock.pay(any(), anyDouble())).thenReturn("1"); 
		
		//if we wanted to keep this for some reason (even though it won't get used, so don't ask me why), 
		//we can add this prefix: "lenient" like this: 
		lenient().when(paymentServiceMock.pay(any(), anyDouble())).thenReturn("1"); 
		
		
		// when
		bookingService.makeBooking(bookingRequest);

		// then
		// no exception is thrown

	}// should_InvokePayment_When_Prepaid()
	
}// Test13StrictStubbing class
