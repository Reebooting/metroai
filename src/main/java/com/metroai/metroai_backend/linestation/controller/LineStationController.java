package com.metroai.metroai_backend.linestation.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metroai.metroai_backend.linestation.dto.CreateLineStationRequest;
import com.metroai.metroai_backend.linestation.dto.LineStationResponse;
import com.metroai.metroai_backend.linestation.service.LineStationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/line-stations")
@RequiredArgsConstructor
public class LineStationController {

    private final LineStationService lineStationService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public LineStationResponse createLineStation(
            @Valid @RequestBody
            CreateLineStationRequest request
    ) {

        return lineStationService.createLineStation(request);
    }

    @GetMapping("/line/{lineId}")
    public List<LineStationResponse> getStationsByLine(
            @PathVariable Long lineId
    ) {

        return lineStationService.getStationsByLine(lineId);
    }
}