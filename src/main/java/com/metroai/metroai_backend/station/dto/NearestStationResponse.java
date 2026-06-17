package com.metroai.metroai_backend.station.dto;

public record NearestStationResponse(

        Long stationId,

        String stationName,

        Double distanceKm

) {
}