package com.metroai.metroai_backend.journey.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.metroai.metroai_backend.exception.ResourceNotFoundException;
import com.metroai.metroai_backend.journey.dto.JourneyResponse;
import com.metroai.metroai_backend.journey.dto.RouteResponse;
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

        LineStation source
                = lineStationRepository
                        .findByLineIdAndStationId(
                                lineId,
                                sourceStationId
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Source station not found on line"
                                )
                        );

        LineStation destination
                = lineStationRepository
                        .findByLineIdAndStationId(
                                lineId,
                                destinationStationId
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Destination station not found on line"
                                )
                        );

        int stationsBetween
                = Math.abs(
                        destination.getStationOrder()
                        - source.getStationOrder()
                );

        double distance
                = Math.round(
                        Math.abs(
                                destination.getDistanceFromStart()
                                - source.getDistanceFromStart()
                        ) * 100.0
                ) / 100.0;

        return new JourneyResponse(
                source.getStation().getName(),
                destination.getStation().getName(),
                stationsBetween,
                distance,
                stationsBetween * 2,
                0,
                List.of(
                        source.getStation().getName(),
                        destination.getStation().getName()
                )
        );
    }

    public RouteResponse getRoute(
            Long lineId,
            Long sourceStationId,
            Long destinationStationId
    ) {

        LineStation source
                = lineStationRepository
                        .findByLineIdAndStationId(
                                lineId,
                                sourceStationId
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Source station not found on line"
                                )
                        );

        LineStation destination
                = lineStationRepository
                        .findByLineIdAndStationId(
                                lineId,
                                destinationStationId
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Destination station not found on line"
                                )
                        );

        int sourceOrder = source.getStationOrder();
        int destinationOrder = destination.getStationOrder();

        int startOrder
                = Math.min(
                        sourceOrder,
                        destinationOrder
                );

        int endOrder
                = Math.max(
                        sourceOrder,
                        destinationOrder
                );

        List<LineStation> stations
                = lineStationRepository
                        .findByLineIdAndStationOrderBetweenOrderByStationOrderAsc(
                                lineId,
                                startOrder,
                                endOrder
                        );

        List<String> route
                = stations.stream()
                        .map(ls
                                -> ls.getStation()
                                .getName()
                        )
                        .toList();

        if (sourceOrder > destinationOrder) {

            route
                    = new java.util.ArrayList<>(
                            route
                    );

            java.util.Collections.reverse(
                    route
            );
        }

        int totalStations
                = Math.abs(
                        destinationOrder
                        - sourceOrder
                );

        double totalDistanceKm
                = totalStations * 5.0;

        int estimatedTimeMinutes
                = totalStations * 2;

        return new RouteResponse(
                source.getStation().getName(),
                destination.getStation().getName(),
                totalStations,
                totalDistanceKm,
                estimatedTimeMinutes,
                0,
                route
        );
    }

}
