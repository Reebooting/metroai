package com.metroai.metroai_backend.station.dto;

import com.metroai.metroai_backend.station.entity.MetroLine;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateStationRequest(

        @NotBlank
        String name,

        @NotBlank
        String code,

        @NotNull
        MetroLine line,

        Double latitude,

        Double longitude,

        Boolean isInterchange

) {
}