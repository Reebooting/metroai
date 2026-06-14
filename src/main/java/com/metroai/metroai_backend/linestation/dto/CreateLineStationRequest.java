package com.metroai.metroai_backend.linestation.dto;

import jakarta.validation.constraints.NotNull;

public record CreateLineStationRequest(

        @NotNull
        Long lineId,

        @NotNull
        Long stationId,

        @NotNull
        Integer stationOrder,

        Double distanceFromStart

) {
}