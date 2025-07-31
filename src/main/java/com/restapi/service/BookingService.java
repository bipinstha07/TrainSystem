package com.restapi.service;

import com.restapi.dto.BookingDto;
import com.restapi.dto.BookingRequestDto;
import com.restapi.dto.BookingResponseDto;

public interface BookingService {
    BookingResponseDto save(BookingRequestDto bookingRequestDto);

    BookingDto findByPnr(String pnr);

    BookingDto getBooking(long bookingId);

    void delete(long bookingId);
}
