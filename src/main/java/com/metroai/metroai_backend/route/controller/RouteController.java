package com.metroai.metroai_backend.route.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metroai.metroai_backend.route.dto.RouteResponse;
import com.metroai.metroai_backend.route.service.RouteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping
    public RouteResponse route(

            @RequestParam Long sourceStationId,

            @RequestParam Long destinationStationId

    ) {

        return routeService.findRoute(
                sourceStationId,
                destinationStationId
        );
    }
}