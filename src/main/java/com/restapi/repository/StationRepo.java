package com.restapi.repository;

import com.restapi.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepo extends JpaRepository<Station,Long> {
}
