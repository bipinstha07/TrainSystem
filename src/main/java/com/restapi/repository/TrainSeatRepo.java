package com.restapi.repository;

import com.restapi.entity.TrainSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainSeatRepo extends JpaRepository<TrainSeat,Long> {

    @Query("Select ts from TrainSeat ts where ts.trainSchedule.id=?1 order by ts.seatOrder")
    List<TrainSeat> findByTrainScheduleId(Long trainScheduleId);

}
