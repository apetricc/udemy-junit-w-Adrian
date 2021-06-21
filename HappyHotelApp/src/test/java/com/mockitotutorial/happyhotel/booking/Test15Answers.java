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
class Test15Answers {
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
			double expected = 400.0 * .8;
			//this test doesn't really test much, it will always pass we test whether our static returns what we think it should return
//			mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble())).thenReturn(400.0);
			//we can return a value based on the input, with "answers"
			mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble())).thenAnswer(inv -> (double) inv.getArgument(0) * .8);
			//so this line (above) means that the 'toEuro' method should take the input argument (the input dollar price) & return 80% of the input...
			//that's the magic behind answers in Mockito, but you probably will never need to use them, it's an advanced concept; 
			
			// when
			double actual = bookingService.calculatePriceEuro(bookingRequest);
	
			// then
			assertEquals(expected, actual);
		}
	}// should_CalculateCorrectPrice()
	
}// Test14StaticMethods class
