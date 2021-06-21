package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;


//golden rule: we can mock all the other classes the class we are testing has dependencies on, 
	//but we should NEVER mock the class we are testing.  Then we'd be testing the mock...
	

class Test09MockingVoidMethods {

	private BookingService bookingService;
	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;

	@BeforeEach
	void setup() {

		this.paymentServiceMock = mock(PaymentService.class);
		this.roomServiceMock = mock(RoomService.class);
		this.bookingDAOMock = mock(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);
		this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
	}

	@Test
	void should_ThrowExeption_When_MailNotReady() {
		//given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
		when(this.roomServiceMock.findAvailableRoomId(bookingRequest))
			.thenThrow(BusinessException.class);
		
		/**
		 * we might want to try throwing an exception for the sendBookingConfirmation method, but this won't work
		 * b/c it is a void method: 
		 * when(this.mailSenderMock.sendBookingConfirmation(any())).thenThrow(BusinessException.class);
		 * 
		 * to do this we have to use "doThrow(BusinessClassException.class)"
		 */ 
		//like this (which is similar to the spies method 'doReturn', and in fact we can use 'doThrow' with Spies: 
		doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any());
		
		//when
		Executable executable = () -> bookingService.makeBooking(bookingRequest); 
		
		//then
		assertThrows(BusinessException.class, executable);
		
	}//should_ThrowExeption_When_MailNotReady()
	
	@Test
	void should_NotThrowExeption_When_MailNotReady() {
		//given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
		//it will fail b/c it expected an exception that was not thrown
		doNothing().when(mailSenderMock).sendBookingConfirmation(any());
		
		//when
		bookingService.makeBooking(bookingRequest); 
		
		//then
		//no exception is thrown
		
	}//should_NotThrowExeption_When_MailNotReady()

	
	/**
	 * To sum up, if you want to throw an exception from a void method, you need to use the do -> throw -> when pattern; 
	 * and you can also make sure that a void method does not do anything by using 'doNothing()', which is the default behavior for a void method,
	 * which means you could actually just not have a test there for it (remove the doNothing() line above) and the test would pass; 
	 */

}//Test09MockingVoidMethods class 
