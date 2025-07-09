package com.restapi.repository;

import com.restapi.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepo extends JpaRepository<Train,Long> {
}
