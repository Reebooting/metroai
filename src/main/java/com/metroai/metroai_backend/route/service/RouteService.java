package com.metroai.metroai_backend.route.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.metroai.metroai_backend.exception.ResourceNotFoundException;
import com.metroai.metroai_backend.graph.service.GraphService;
import com.metroai.metroai_backend.linestation.entity.LineStation;
import com.metroai.metroai_backend.linestation.repository.LineStationRepository;
import com.metroai.metroai_backend.route.dto.JourneyInstructionResponse;
import com.metroai.metroai_backend.route.dto.RouteResponse;
import com.metroai.metroai_backend.route.dto.RouteSegmentResponse;
import com.metroai.metroai_backend.route.dto.RouteStationResponse;
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

        List<Station> stations = stationRepository.findAllById(path);

        List<RouteStationResponse> route =
        path.stream()
                .map(id -> {

                    Station station =
                            stationRepository
                                    .findById(id)
                                    .orElseThrow();

                    return new RouteStationResponse(
                            station.getId(),
                            station.getName(),
                            station.getIsInterchange()
                    );
                })
                .toList();

        List<JourneyInstructionResponse> instructions = buildInstructions(path);
        
        List<RouteSegmentResponse> segments = buildSegments(path);

        int totalStations = path.size() - 1;

        double totalDistanceKm =
        calculateTotalDistance(
                path
        );

        int interchangeCount = calculateInterchanges(path );

        int estimatedTimeMinutes = calculateTravelTime(totalDistanceKm,interchangeCount );

        int fare =calculateFare( totalDistanceKm );
        

        return new RouteResponse(
                source.getId(),
                source.getName(),
                destination.getId(),
                destination.getName(),
                totalStations,
                totalDistanceKm,
                estimatedTimeMinutes,
                interchangeCount,
                fare,
                route,
                instructions,
                segments
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

private List<JourneyInstructionResponse>
buildInstructions(
        List<Long> path
) {

    List<JourneyInstructionResponse> instructions =
            new ArrayList<>();

    if (path.size() < 2) {

        return instructions;
    }

    int step = 1;

    Station source =
            stationRepository
                    .findById(path.get(0))
                    .orElseThrow();

    String currentLine =
            getLineNameBetweenStations(
                    path.get(0),
                    path.get(1)
            );

    instructions.add(
            new JourneyInstructionResponse(
                    step++,
                    "Board "
                            + currentLine
                            + " at "
                            + source.getName()
            )
    );

    for (
            int i = 1;
            i < path.size() - 1;
            i++
    ) {

        String nextLine =
                getLineNameBetweenStations(
                        path.get(i),
                        path.get(i + 1)
                );

        if (
                !currentLine.equals(
                        nextLine
                )
        ) {

            Station interchange =
                    stationRepository
                            .findById(path.get(i))
                            .orElseThrow();

            instructions.add(
                    new JourneyInstructionResponse(
                            step++,
                            "Change from "
                                    + currentLine
                                    + " to "
                                    + nextLine
                                    + " at "
                                    + interchange.getName()
                    )
            );

            currentLine = nextLine;
        }
    }

    Station destination =
            stationRepository
                    .findById(
                            path.get(path.size() - 1)
                    )
                    .orElseThrow();

    instructions.add(
            new JourneyInstructionResponse(
                    step,
                    "Arrive at "
                            + destination.getName()
            )
    );

    return instructions;
}

private String getLineNameBetweenStations(
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

                return a.getLine().getName();
            }
        }
    }

    return "Unknown Line";
}

private List<RouteSegmentResponse>
buildSegments(
        List<Long> path
) {

    List<RouteSegmentResponse> segments =
            new ArrayList<>();

    if (path.size() < 2) {
        return segments;
    }

    String currentLine =
            getLineNameBetweenStations(
                    path.get(0),
                    path.get(1)
            );

    Long segmentStart =
            path.get(0);

    int stationCount = 0;

    for (
            int i = 0;
            i < path.size() - 1;
            i++
    ) {

        String line =
                getLineNameBetweenStations(
                        path.get(i),
                        path.get(i + 1)
                );

        if (
                !line.equals(currentLine)
        ) {

            Station from =
                    stationRepository
                            .findById(segmentStart)
                            .orElseThrow();

            Station to =
                    stationRepository
                            .findById(path.get(i))
                            .orElseThrow();

            segments.add(
                    new RouteSegmentResponse(
                            currentLine,
                            from.getName(),
                            to.getName(),
                            stationCount
                    )
            );

            currentLine = line;
            segmentStart = path.get(i);
            stationCount = 1;

        } else {

            stationCount++;
        }
    }

    Station from =
            stationRepository
                    .findById(segmentStart)
                    .orElseThrow();

    Station to =
            stationRepository
                    .findById(
                            path.get(path.size() - 1)
                    )
                    .orElseThrow();

    segments.add(
            new RouteSegmentResponse(
                    currentLine,
                    from.getName(),
                    to.getName(),
                    stationCount
            )
    );

    return segments;
}

private int calculateFare(
        double distanceKm
) {

    if (distanceKm <= 2) {
        return 10;
    }

    if (distanceKm <= 5) {
        return 20;
    }

    if (distanceKm <= 12) {
        return 30;
    }

    if (distanceKm <= 21) {
        return 40;
    }

    return 50;
}

}