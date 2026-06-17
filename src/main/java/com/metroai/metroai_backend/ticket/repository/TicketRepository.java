package com.metroai.metroai_backend.ticket.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metroai.metroai_backend.ticket.entity.Ticket;

public interface TicketRepository
        extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUserId(
            UUID userId
    );
}