package com.metroai.metroai_backend.ticket.dto;

import jakarta.validation.constraints.NotNull;

public record BookTicketRequest(

        @NotNull
        Long sourceStationId,

        @NotNull
        Long destinationStationId

) {
}