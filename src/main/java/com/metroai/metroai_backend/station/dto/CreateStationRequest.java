package com.metroai.metroai_backend.station.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateStationRequest(

        @NotBlank
        String name,

        @NotBlank
        String code,

        Double latitude,

        Double longitude,

        Boolean isInterchange

) {
}