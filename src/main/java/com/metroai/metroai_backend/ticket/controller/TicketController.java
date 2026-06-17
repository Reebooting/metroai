package com.metroai.metroai_backend.ticket.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metroai.metroai_backend.ticket.dto.BookTicketRequest;
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

        return ticketService.bookTicket(
                request
        );
    }
}