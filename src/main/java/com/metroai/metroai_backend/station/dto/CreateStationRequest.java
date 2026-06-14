package com.metroai.metroai_backend.station.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateStationRequest(

        @NotBlank
        String name,

        @NotBlank
        String code,

        @NotNull
        Long lineId,

        Double latitude,

        Double longitude,

        Boolean isInterchange

) {
}