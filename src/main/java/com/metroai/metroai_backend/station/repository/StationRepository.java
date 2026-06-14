package com.metroai.metroai_backend.station.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metroai.metroai_backend.station.entity.Station;

public interface StationRepository
        extends JpaRepository<Station, Long> {

    Optional<Station> findByCode(String code);
    boolean existsByCode(String code);

List<Station> findByNameContainingIgnoreCase(
        String name
);

}