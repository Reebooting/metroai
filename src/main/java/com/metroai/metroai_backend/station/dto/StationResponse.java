package com.metroai.metroai_backend.station.dto;

public record StationResponse(

        Long id,

        String name,

        String code,

        Double latitude,

        Double longitude,

        Boolean isInterchange

) {
}