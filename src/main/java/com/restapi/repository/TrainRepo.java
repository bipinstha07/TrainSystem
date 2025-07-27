package com.restapi.repository;

import com.restapi.entity.Train;
import com.restapi.entity.TrainRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainRepo extends JpaRepository<Train,Long> {


    @Query("""
    SELECT  tr from TrainRoute tr
    WHERE tr.station.id = :sourceStationId OR tr.station.id = :destinationId
    GROUP BY  tr.train.id
    HAVING  SUM(CASE WHEN tr.station.id = :sourceStationId THEN 1 ELSE 0 END )>0\s
    AND SUM(CASE WHEN tr.station.id = :destinationId THEN 1 ELSE 0 END )>0\s
    AND (  MIN(CASE WHEN tr.station.id=:sourceStationID THEN tr.stationOrder ELSE 99999999 END) < MIN(CASE WHEN tr.station.id=:destinationID THEN tr.stationOrder ELSE 99999999 END)    )
   \s
""")
    List<TrainRoute> findTrainBySourceAndDestinationInOrder(@Param("sourceStationId") Long sourceStationId, @Param("destinationId") Long destinationId);

}
