package com.restapi.repository;

import com.restapi.entity.Train;
import com.restapi.entity.TrainRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainRouteRepo extends JpaRepository<TrainRoute,Long> {
   List<TrainRoute> findByTrainId(Long trainId);
}
