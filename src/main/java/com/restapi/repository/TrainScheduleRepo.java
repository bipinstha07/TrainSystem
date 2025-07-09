package com.restapi.repository;

import com.restapi.entity.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainScheduleRepo extends JpaRepository<TrainSchedule,Long> {

    @Query("Select ts from TrainSchedule ts where ts.train.id=?1")
    List<TrainSchedule> findByTrainId(Long trainId);

}
