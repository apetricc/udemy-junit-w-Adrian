package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.*;
import org.junit.jupiter.*;
import org.junit.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

class Test02DefaultReturnValues {

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
		
		//println of some of the mocks to see what's going on in here: 
		System.out.println("List returned: " + roomServiceMock.getAvailableRooms());
		System.out.println("Available roomID returned: " + roomServiceMock.findAvailableRoomId(null));
		System.out.println("Primitive getRoomCount returned: " + roomServiceMock.getRoomCount());
	}

	@Test
	void should_CountAvailablePlaces() {
		//given
		// we would expect 0 to be returned b/c Mockito will return an empty list of Rooms in its mock of the getAvailablePlaceCount() method; 
		//Mockito returns "nice mocks" ... not sure what that means exactly, excpet that it returns same type that should work-->
		// "nice mocks" mean: 1. empty list, 2. null Object 3. 0/false primitives as DEFAULT values, we can also specify return values w/ Mockito; 
		int expected = 0;
		
		//when
		int actual = bookingService.getAvailablePlaceCount();
		
		//then
		assertEquals(expected, actual);
		
	}//should_CalculateCorrectPrice_When_CorrectInput()

}//Test02DefaultReturnValues class
