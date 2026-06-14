package com.metroai.metroai_backend.journey.service;

import org.springframework.stereotype.Service;

import com.metroai.metroai_backend.exception.ResourceNotFoundException;
import com.metroai.metroai_backend.journey.dto.JourneyResponse;
import com.metroai.metroai_backend.linestation.entity.LineStation;
import com.metroai.metroai_backend.linestation.repository.LineStationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JourneyService {

    private final LineStationRepository lineStationRepository;

    public JourneyResponse calculateJourney(
            Long lineId,
            Long sourceStationId,
            Long destinationStationId
    ) {

        LineStation source =
                lineStationRepository
                        .findByLineIdAndStationId(
                                lineId,
                                sourceStationId
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Source station not found on line"
                                )
                        );

        LineStation destination =
                lineStationRepository
                        .findByLineIdAndStationId(
                                lineId,
                                destinationStationId
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Destination station not found on line"
                                )
                        );

        int stationsBetween =
                Math.abs(
                        destination.getStationOrder()
                                - source.getStationOrder()
                );

        double distance =
        Math.round(
                Math.abs(
                        destination.getDistanceFromStart()
                                - source.getDistanceFromStart()
                ) * 100.0
        ) / 100.0;

        return new JourneyResponse(
                source.getStation().getName(),
                destination.getStation().getName(),
                stationsBetween,
                distance
        );
    }
}