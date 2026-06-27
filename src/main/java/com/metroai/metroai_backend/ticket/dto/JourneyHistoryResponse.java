package com.metroai.metroai_backend.ticket.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record JourneyHistoryResponse(

        Long ticketId,

        UUID userId,

        String sourceStation,

        String destinationStation,

        Double fare,

        String status,

        LocalDateTime bookedAt

) {
}