package com.metroai.metroai_backend.ticket.dto;
import java.util.UUID;
public record TicketResponse(

        Long ticketId,

        UUID userId,

        String sourceStation,

        String destinationStation,

        Double fare,

        String status

) {
}