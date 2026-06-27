package com.metroai.metroai_backend.journey.dto;

import java.time.LocalDateTime;

public record JourneyHistoryResponse(

        Long ticketId,

        String sourceStation,

        String destinationStation,

        Double fare,

        String ticketStatus,

        LocalDateTime bookedAt

) {
}