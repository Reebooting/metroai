package com.metroai.metroai_backend.journey.dto;

public record JourneyResponse(

        String source,

        String destination,

        Integer stationsBetween,

        Double distance

) {
}