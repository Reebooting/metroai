package com.metroai.metroai_backend.graph.dto;

import java.util.List;

public record StationNode(

        Long stationId,

        String stationName,

        List<Long> neighborIds

) {
}