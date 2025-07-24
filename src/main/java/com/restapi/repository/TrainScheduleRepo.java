package com.restapi.repository;

import com.restapi.entity.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TrainScheduleRepo extends JpaRepository<TrainSchedule,Long> {

    @Query("Select ts from TrainSchedule ts where ts.train.id=?1")
    List<TrainSchedule> findByTrainId(Long trainId);

    @Query("Select ts from TrainSchedule  ts where ts.train=?1 AND  ts.runDate=?2")
     Optional<TrainSchedule> findByTrainIdAndRunDate(Long trainId, LocalDate runDate);

}
