package com.metroai.metroai_backend.linestation.dto;

public record LineStationResponse(

        Long id,

        Long lineId,

        String lineName,

        Long stationId,

        String stationName,

        Integer stationOrder,

        Double distanceFromStart

) {
}