package com.metroai.metroai_backend.route.dto;

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

        List<String> route

) {
}