package com.metroai.metroai_backend.linestation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.metroai.metroai_backend.exception.DuplicateResourceException;
import com.metroai.metroai_backend.exception.ResourceNotFoundException;
import com.metroai.metroai_backend.line.entity.Line;
import com.metroai.metroai_backend.line.repository.LineRepository;
import com.metroai.metroai_backend.linestation.dto.CreateLineStationRequest;
import com.metroai.metroai_backend.linestation.dto.LineStationResponse;
import com.metroai.metroai_backend.linestation.entity.LineStation;
import com.metroai.metroai_backend.linestation.repository.LineStationRepository;
import com.metroai.metroai_backend.station.entity.Station;
import com.metroai.metroai_backend.station.repository.StationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LineStationService {

    private final LineStationRepository lineStationRepository;
    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    public LineStationResponse createLineStation(
            CreateLineStationRequest request
    ) {

        if (lineStationRepository
                .existsByLineIdAndStationId(
                        request.lineId(),
                        request.stationId()
                )) {

            throw new DuplicateResourceException(
                    "Station already assigned to line"
            );
        }

        Line line
                = lineRepository
                        .findById(request.lineId())
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Line not found"
                                )
                        );

        Station station
                = stationRepository
                        .findById(request.stationId())
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Station not found"
                                )
                        );

        LineStation lineStation
                = LineStation.builder()
                        .line(line)
                        .station(station)
                        .stationOrder(
                                request.stationOrder()
                        )
                        .distanceFromStart(
                                request.distanceFromStart()
                        )
                        .createdAt(LocalDateTime.now())
                        .build();

        LineStation saved
                = lineStationRepository.save(
                        lineStation
                );

        return new LineStationResponse(
                saved.getId(),
                saved.getLine().getId(),
                saved.getLine().getName(),
                saved.getStation().getId(),
                saved.getStation().getName(),
                saved.getStationOrder(),
                saved.getDistanceFromStart()
        );
    }

    public List<LineStationResponse> getStationsByLine(
            Long lineId
    ) {

        return lineStationRepository
                .findByLineIdOrderByStationOrderAsc(
                        lineId
                )
                .stream()
                .map(lineStation -> new LineStationResponse(
                lineStation.getId(),
                lineStation.getLine().getId(),
                lineStation.getLine().getName(),
                lineStation.getStation().getId(),
                lineStation.getStation().getName(),
                lineStation.getStationOrder(),
                lineStation.getDistanceFromStart()
        ))
                .toList();
    }

}
