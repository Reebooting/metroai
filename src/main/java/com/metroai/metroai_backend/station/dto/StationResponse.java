package com.metroai.metroai_backend.station.dto;

import com.metroai.metroai_backend.station.entity.MetroLine;

public record StationResponse(

        Long id,

        String name,

        String code,

        MetroLine line,

        Double latitude,

        Double longitude,

        Boolean isInterchange

) {
}