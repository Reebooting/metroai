package com.metroai.metroai_backend.journey.dto;

import java.util.List;

public record RouteResponse(

        String source,

        String destination,

        Integer totalStations,

        Double totalDistanceKm,

        Integer estimatedTimeMinutes,

        Integer interchangeCount,

        List<String> route

) {
}