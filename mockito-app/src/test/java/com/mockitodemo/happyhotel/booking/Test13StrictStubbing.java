package com.mockitodemo.happyhotel.booking;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Test13StrictStubbing {

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

	@Captor
	private ArgumentCaptor<Double> doubleCaptor;

	@Test
	void should_InvokePayment_When_Prepaid() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
				LocalDate.of(2020, 01, 05), 2, false);
		lenient().when(paymentServiceMock.pay(any(), anyDouble())).thenReturn("1");

		// when
		bookingService.makeBooking(bookingRequest);

		// then
		// no exception is thrown
	}

}
