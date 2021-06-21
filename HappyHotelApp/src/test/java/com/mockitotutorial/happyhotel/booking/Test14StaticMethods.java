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
class Test14StaticMethods {
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
	@Captor
	private ArgumentCaptor<BookingRequest> bookingRequestCaptor;

	
	@Test
	void should_CalculateCorrectPrice() {
		try (MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)) {
			
		
			// given
			BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
					LocalDate.of(2020, 01, 05), 2, false);
			double expected = 400.0;
			mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble())).thenReturn(400.0);
			
			// when
			double actual = bookingService.calculatePriceEuro(bookingRequest);
	
			// then
			assertEquals(expected, actual);
		}
	}// should_CalculateCorrectPrice()
	
}// Test14StaticMethods class
