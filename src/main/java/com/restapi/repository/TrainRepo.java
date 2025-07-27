package com.restapi.repository;

import com.restapi.entity.Train;
import com.restapi.entity.TrainRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainRepo extends JpaRepository<Train,Long> {


    @Query("SELECT tr.train from TrainRoute tr WHERE tr.station.id = :sourceStationId OR tr.station.id = :destinationId")
    List<Train> findTrainBySourceAndDestination(@Param("sourceStationId") Long sourceStationId, @Param("destinationId") Long destinationId);

}
