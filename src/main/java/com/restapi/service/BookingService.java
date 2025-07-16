package com.restapi.service;

import com.restapi.dto.BookingDto;

public interface BookingService {
    BookingDto save(BookingDto bookingDto);

    BookingDto findByPnr(String pnr);

    BookingDto getBooking(long bookingId);

    void delete(long bookingId);
}
