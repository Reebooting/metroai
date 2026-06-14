package com.metroai.metroai_backend.journey.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metroai.metroai_backend.journey.dto.JourneyResponse;
import com.metroai.metroai_backend.journey.dto.RouteResponse;
import com.metroai.metroai_backend.journey.service.JourneyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/journey")
@RequiredArgsConstructor
public class JourneyController {

    private final JourneyService journeyService;

    @GetMapping
    public JourneyResponse calculateJourney(

            @RequestParam Long lineId,

            @RequestParam Long sourceStationId,

            @RequestParam Long destinationStationId

    ) {

        return journeyService.calculateJourney(
                lineId,
                sourceStationId,
                destinationStationId
        );
    }

@GetMapping("/route")
public RouteResponse getRoute(

        @RequestParam Long lineId,

        @RequestParam Long sourceStationId,

        @RequestParam Long destinationStationId

) {

    return journeyService.getRoute(
            lineId,
            sourceStationId,
            destinationStationId
    );
}

}