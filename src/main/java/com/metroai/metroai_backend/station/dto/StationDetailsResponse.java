package com.metroai.metroai_backend.station.dto;

public record StationDetailsResponse(

        Long id,

        String name,

        String code,

        String lineName,

        String lineCode,

        Integer stationOrder,

        Double distanceFromStart,

        Double latitude,

        Double longitude,

        Boolean isInterchange

) {
}