package com.metroai.metroai_backend.route.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.metroai.metroai_backend.exception.ResourceNotFoundException;
import com.metroai.metroai_backend.graph.service.GraphService;
import com.metroai.metroai_backend.linestation.entity.LineStation;
import com.metroai.metroai_backend.linestation.repository.LineStationRepository;
import com.metroai.metroai_backend.route.dto.RouteResponse;
import com.metroai.metroai_backend.station.entity.Station;
import com.metroai.metroai_backend.station.repository.StationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final GraphService graphService;
    private final LineStationRepository lineStationRepository;
    private final StationRepository stationRepository;

    public RouteResponse findRoute( Long sourceStationId,Long destinationStationId
    ) {

        // BFS path
        List<Long> path = graphService.findShortestPath( sourceStationId, destinationStationId);

        Station source = stationRepository.findById(sourceStationId).orElseThrow(
                                () -> new ResourceNotFoundException("Source station not found"));

        Station destination = stationRepository.findById(destinationStationId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Destination station not found"));

        List<Station> stations =
        stationRepository.findAllById(path);

        List<String> route = path.stream().map(id ->stations.stream()
                     .filter(station ->station.getId().equals(id)).findFirst().orElseThrow().getName()).toList();

        int totalStations = path.size() - 1;

        double totalDistanceKm =
        calculateTotalDistance(
                path
        );

        int interchangeCount = calculateInterchanges(path );

        int estimatedTimeMinutes = calculateTravelTime(totalDistanceKm,interchangeCount );

        

        return new RouteResponse(
                source.getId(),
                source.getName(),
                destination.getId(),
                destination.getName(),
                totalStations,
                totalDistanceKm,
                estimatedTimeMinutes,
                interchangeCount,
                route
        );
    }

private int calculateInterchanges(
        List<Long> path
) {

    if (path.size() < 2) {

        return 0;
    }

    int interchangeCount = 0;

    Long previousLine =
            findCommonLine(
                    path.get(0),
                    path.get(1)
            );

    for (
            int i = 1;
            i < path.size() - 1;
            i++
    ) {

        Long currentLine =
                findCommonLine(
                        path.get(i),
                        path.get(i + 1)
                );

        if (
                previousLine != null
                &&
                currentLine != null
                &&
                !previousLine.equals(
                        currentLine
                )
        ) {

            interchangeCount++;
        }

        previousLine = currentLine;
    }

    return interchangeCount;
}

private Long findCommonLine(
        Long stationA,
        Long stationB
) {

    List<LineStation> first =
            lineStationRepository
                    .findByStationId(stationA);

    List<LineStation> second =
            lineStationRepository
                    .findByStationId(stationB);

    for (LineStation a : first) {

        for (LineStation b : second) {

            if (
                a.getLine().getId()
                 .equals(
                    b.getLine().getId()
                 )
            ) {

                return a.getLine().getId();
            }
        }
    }

    return null;
}

private double calculateDistanceBetweenStations(
        Long stationA,
        Long stationB
) {

    List<LineStation> first =
            lineStationRepository
                    .findByStationId(stationA);

    List<LineStation> second =
            lineStationRepository
                    .findByStationId(stationB);

    for (LineStation a : first) {

        for (LineStation b : second) {

            if (
                    a.getLine().getId()
                            .equals(
                                    b.getLine().getId()
                            )
            ) {

                return Math.abs(
                        a.getDistanceFromStart()
                                - b.getDistanceFromStart()
                );
            }
        }
    }

    return 0.0;
}

private double calculateTotalDistance(
        List<Long> path
) {

    double totalDistance = 0.0;

    for (
            int i = 0;
            i < path.size() - 1;
            i++
    ) {

        totalDistance +=
                calculateDistanceBetweenStations(
                        path.get(i),
                        path.get(i + 1)
                );
    }

    return Math.round(
            totalDistance * 100.0
    ) / 100.0;
}

private int calculateTravelTime(
        double distanceKm,
        int interchangeCount
) {

    double minutes =
            (distanceKm / 35.0) * 60.0;

    return (int) Math.ceil(
            minutes
    ) + (interchangeCount * 5);
}

}