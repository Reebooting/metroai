package com.metroai.metroai_backend.linestation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metroai.metroai_backend.linestation.entity.LineStation;

public interface LineStationRepository
        extends JpaRepository<LineStation, Long> {

    List<LineStation> findByLineIdOrderByStationOrderAsc(
            Long lineId
    );
boolean existsByLineIdAndStationId(
        Long lineId,
        Long stationId
);

Optional<LineStation>findByLineIdAndStationId(
        Long lineId,
        Long stationId
);

Optional<LineStation>
findFirstByStationId(
        Long stationId
);

List<LineStation>
findByLineIdAndStationOrderBetweenOrderByStationOrderAsc(
        Long lineId,
        Integer startOrder,
        Integer endOrder
);

List<LineStation> findAllByOrderByLineIdAscStationOrderAsc();

List<LineStation> findByStationId(
        Long stationId
);

}