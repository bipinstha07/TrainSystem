package com.restapi.repository;

import com.restapi.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingRepo extends JpaRepository<Booking,Long> {

//    @Query("Select ts from Booking ts where ts.pnr=?1")
    Booking findBookingByPnr(String pnr);

}
