package com.metroai.metroai_backend.route.dto;
import java.io.Serializable;

public record RouteSegmentResponse(

        String lineName,

        String fromStation,

        String toStation,

        Integer stationCount

) implements Serializable {
}