package com.metroai.metroai_backend.ticket.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metroai.metroai_backend.ticket.dto.BookTicketRequest;
import com.metroai.metroai_backend.ticket.dto.JourneyHistoryResponse;
import com.metroai.metroai_backend.ticket.dto.TicketResponse;
import com.metroai.metroai_backend.ticket.service.TicketService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public TicketResponse bookTicket(
            @Valid
            @RequestBody
            BookTicketRequest request
    ) {

        return ticketService.bookTicket(request);
    }

    @GetMapping("/{ticketId}")
    public TicketResponse getTicket(
            @PathVariable Long ticketId
    ) {

        return ticketService.getTicket(ticketId);
    }

    @GetMapping("/history")
    public List<JourneyHistoryResponse> getJourneyHistory() {

        return ticketService.getJourneyHistory();
    }

    @PutMapping("/{ticketId}/cancel")
    public TicketResponse cancelTicket(
            @PathVariable Long ticketId
    ) {

        return ticketService.cancelTicket(ticketId);
    }

    @PutMapping("/{ticketId}/validate")
    public TicketResponse validateTicket(
            @PathVariable Long ticketId
    ) {

        return ticketService.validateTicket(ticketId);
    }

    @GetMapping(
            value = "/{ticketId}/qr",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public ResponseEntity<byte[]> getQrCode(
            @PathVariable Long ticketId
    ) {

        return ResponseEntity.ok(
                ticketService.generateQrCode(ticketId)
        );
    }
}