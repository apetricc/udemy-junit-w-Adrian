package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.*;


//golden rule: we can mock all the other classes the class we are testing has dependencies on, 
	//but we should NEVER mock the class we are testing.  Then we'd be testing the mock...
	

class Test08Spies {

	private BookingService bookingService;
	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;

	@BeforeEach
	void setup() {

		this.paymentServiceMock = mock(PaymentService.class);
		this.roomServiceMock = mock(RoomService.class);
		this.bookingDAOMock = spy(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);
		this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
	}

	
	/*
	 * 	Mock = dummy object w/ no real logic that returns nulls or empty lists unless we change their behavior ourselves;
	 *  spy = 'real' object with real logic that we can modify; call the actual methods from the actual classes; 
	 */ 
	@Test
	void should_MakeBooking_When_InputOK() {
		//given
		//
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);		
		
		//when
		String bookingId = bookingService.makeBooking(bookingRequest);
		
		//then
		verify(bookingDAOMock).save(bookingRequest);
		System.out.println("bookingId= " + bookingId);
		
	}//should_MakeBooking_When_InputOK()
	
	
	/*
	 * Mocks: when(mock.method()).thenReturn()
	 * Spies: doReturn().when(spy).method()
	 * 
	 * mock (partial mock) = a real object with real methods that we can modify ?? not sure about this line.
	 */
	@Test
	void should_CancelBooking_When_InputOK() {
		//given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		bookingRequest.setRoomId("1.3");
		String bookingId = "1";
		
		doReturn(bookingRequest).when(bookingDAOMock).get(bookingId);
		
		//when
		bookingService.cancelBooking(bookingId);
		//then
		
	}//should_CancelBooking_When_InputOK()
	
}//Test08Spies class
