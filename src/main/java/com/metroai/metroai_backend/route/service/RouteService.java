package com.metroai.metroai_backend.route.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.metroai.metroai_backend.exception.ResourceNotFoundException;
import com.metroai.metroai_backend.graph.service.GraphService;
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

        double totalDistanceKm = totalStations * 5.0;

        int estimatedTimeMinutes = totalStations * 2;

        int interchangeCount = calculateInterchanges(path );

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
    int interchangeCount = 0;

    for (Long stationId : path) {

        int lineCount = lineStationRepository.findByStationId( stationId ).size();

        if (lineCount > 1) {
            interchangeCount++;
        }
    }

    return interchangeCount;
}

}