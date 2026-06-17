package com.metroai.metroai_backend.ticket.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
public record BookTicketRequest(

        @NotNull
        UUID userId,

        @NotNull
        Long sourceStationId,

        @NotNull
        Long destinationStationId

) {
}