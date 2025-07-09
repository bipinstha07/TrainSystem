package com.restapi.repository;

import com.restapi.entity.BookingPassenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingPassengerRepo extends JpaRepository<BookingPassenger,Long> {
}
