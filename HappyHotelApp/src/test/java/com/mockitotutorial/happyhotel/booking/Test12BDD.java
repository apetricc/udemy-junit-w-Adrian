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
class Test12BDD {
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
	void should_CountAvailablePlaces_When_OneRoomAvailable() {
		// given
		given(this.roomServiceMock.getAvailableRooms()).willReturn(Collections.singletonList(new Room("Room 1", 5)));
		int expected = 5;
		// when
		int actual = bookingService.getAvailablePlaceCount();

		// then
		assertEquals(expected, actual);

	}// should_CountAvailablePlaces_When_OneRoomAvailable()

	@Test
	void should_InvokePayment_When_Prepaid() {
		//given
		//the 'prepaid' argument is true, so the paymentServiceMock should get called, and we want to test that it was in fact called; 
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);		
		
		//when
		bookingService.makeBooking(bookingRequest);
		
		//then
		//verify that it was used multiple times specified in args: 
		//instead of 'verify' we can use the alias pattern "then --> should" 
		then(paymentServiceMock).should(times(1)).pay(bookingRequest, 400.0);
		//verifies that the paymentServiceMock was only used ONCE: 
		verifyNoMoreInteractions(paymentServiceMock);
		
	}//should_InvokePayment_When_Prepaid()
	
}// Test12BDD class
