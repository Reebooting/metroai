package com.metroai.metroai_backend.route.dto;

public record RouteSegmentResponse(

        String lineName,

        String fromStation,

        String toStation,

        Integer stationCount

) {
}