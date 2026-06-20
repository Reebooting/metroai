package com.metroai.metroai_backend.ticket.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.metroai.metroai_backend.auth.repository.UserRepository;
import com.metroai.metroai_backend.exception.ResourceNotFoundException;
import com.metroai.metroai_backend.route.dto.RouteResponse;
import com.metroai.metroai_backend.route.service.RouteService;
import com.metroai.metroai_backend.station.entity.Station;
import com.metroai.metroai_backend.station.repository.StationRepository;
import com.metroai.metroai_backend.ticket.dto.BookTicketRequest;
import com.metroai.metroai_backend.ticket.dto.TicketResponse;
import com.metroai.metroai_backend.ticket.entity.Ticket;
import com.metroai.metroai_backend.ticket.entity.TicketStatus;
import com.metroai.metroai_backend.ticket.repository.TicketRepository;
import com.metroai.metroai_backend.ticket.util.QrCodeUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final RouteService routeService;
    private final StationRepository stationRepository;
    private final UserRepository userRepository;
    public TicketResponse bookTicket(
            BookTicketRequest request
    ) {

userRepository.findById(request.userId())
        .orElseThrow(
                () -> new ResourceNotFoundException(
                        "User not found"
                )
        );


        RouteResponse route =
                routeService.findRoute(
                        request.sourceStationId(),
                        request.destinationStationId()
                );

        Station source =
                stationRepository
                        .findById(
                                request.sourceStationId()
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Source station not found"
                                )
                        );

        Station destination =
                stationRepository
                        .findById(
                                request.destinationStationId()
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Destination station not found"
                                )
                        );

        Ticket ticket = Ticket.builder()
                        .userId(
                                request.userId()
                        )
                        .sourceStationId(
                                request.sourceStationId()
                        )
                        .destinationStationId(
                                request.destinationStationId()
                        )
                        .fare(
                                route.fare().doubleValue()
                        )
                        .status(
                                TicketStatus.BOOKED
                        )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .build();

        Ticket savedTicket =
                ticketRepository.save(ticket);

savedTicket.setQrCode(
        QrCodeUtil.generateQrContent(
                savedTicket.getId()
        )
);

savedTicket =
        ticketRepository.save(
                savedTicket
        );

        return new TicketResponse(
                savedTicket.getId(),
                savedTicket.getUserId(),
                source.getName(),
                destination.getName(),
                savedTicket.getFare(),
                savedTicket.getStatus().name(),
                savedTicket.getQrCode()
        );
    }

public TicketResponse getTicket(
        Long ticketId
) {

    Ticket ticket =
            ticketRepository
                    .findById(ticketId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Ticket not found"
                            )
                    );

    Station source =
            stationRepository
                    .findById(
                            ticket.getSourceStationId()
                    )
                    .orElseThrow();

    Station destination =
            stationRepository
                    .findById(
                            ticket.getDestinationStationId()
                    )
                    .orElseThrow();

    return new TicketResponse(
            ticket.getId(),
            ticket.getUserId(),
            source.getName(),
            destination.getName(),
            ticket.getFare(),
            ticket.getStatus().name(),
            ticket.getQrCode()
    );
}

public List<TicketResponse> getUserTickets(
        UUID userId
) {

    return ticketRepository
            .findByUserId(userId)
            .stream()
            .map(ticket -> {

                Station source =
                        stationRepository
                                .findById(
                                        ticket.getSourceStationId()
                                )
                                .orElseThrow();

                Station destination =
                        stationRepository
                                .findById(
                                        ticket.getDestinationStationId()
                                )
                                .orElseThrow();

                return new TicketResponse(
                        ticket.getId(),
                        ticket.getUserId(),
                        source.getName(),
                        destination.getName(),
                        ticket.getFare(),
                        ticket.getStatus().name(),
                        ticket.getQrCode()
                );
            })
            .toList();
}

public TicketResponse cancelTicket(
        Long ticketId
) {

    Ticket ticket =
            ticketRepository
                    .findById(ticketId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Ticket not found"
                            )
                    );

    ticket.setStatus(
            TicketStatus.CANCELLED
    );

    Ticket saved =
            ticketRepository.save(
                    ticket
            );

    Station source =
            stationRepository
                    .findById(
                            saved.getSourceStationId()
                    )
                    .orElseThrow();

    Station destination =
            stationRepository
                    .findById(
                            saved.getDestinationStationId()
                    )
                    .orElseThrow();

    return new TicketResponse(
            saved.getId(),
            saved.getUserId(),
            source.getName(),
            destination.getName(),
            saved.getFare(),
            saved.getStatus().name(),
            saved.getQrCode()
    );
}

}