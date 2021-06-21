package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.*;
import org.junit.jupiter.*;
import org.junit.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;


//golden rule: we can mock all the other classes the class we are testing has dependencies on, 
	//but we should NEVER mock the class we are testing.  Then we'd be testing the mock...
	

class Test01FirstMocks {

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
	void should_CalculateCorrectPrice_When_CorrectInput() {
		//given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
		
		double expected = 4 * 2 * 50.0;
		
		//when
		double actual = bookingService.calculatePrice(bookingRequest);
		
		//then
		assertEquals(expected, actual);
		
	}//should_CalculateCorrectPrice_When_CorrectInput()

}//Test01FirstMocks class
