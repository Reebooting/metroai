package com.metroai.metroai_backend.station.dto;

public record StationResponse(

        Long id,

        String name,

        String code,

        String lineName,
        String lineCode,

        Double latitude,

        Double longitude,

        Boolean isInterchange

) {
}