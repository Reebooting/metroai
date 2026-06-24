package com.metroai.metroai_backend.route.dto;

import java.io.Serializable;
import java.util.List;

public record RouteResponse(

        Long sourceStationId,

        String sourceStation,

        Long destinationStationId,

        String destinationStation,

        Integer totalStations,

        Double totalDistanceKm,

        Integer estimatedTimeMinutes,

        Integer interchangeCount,

        Integer fare,
        

        List<RouteStationResponse> route,

        List<JourneyInstructionResponse> instructions,

        List<RouteSegmentResponse> segments

        

) implements Serializable {
}