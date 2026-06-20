package com.metroai.metroai_backend.ticket.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/{ticketId}")
public TicketResponse getTicket(
        @PathVariable
        Long ticketId
) {

    return ticketService.getTicket(
            ticketId
    );
}

@GetMapping("/user/{userId}")
public List<TicketResponse> getUserTickets(
        @PathVariable
        UUID userId
) {

    return ticketService.getUserTickets(
            userId
    );
}

@PutMapping("/{ticketId}/cancel")
public TicketResponse cancelTicket(
        @PathVariable
        Long ticketId
) {

    return ticketService.cancelTicket(
            ticketId
    );
}

}