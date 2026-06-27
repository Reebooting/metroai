package com.metroai.metroai_backend.line.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metroai.metroai_backend.line.dto.CreateLineRequest;
import com.metroai.metroai_backend.line.dto.LineResponse;
import com.metroai.metroai_backend.line.service.LineService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/lines")
@RequiredArgsConstructor
public class LineController {

    private final LineService lineService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public LineResponse createLine(
            @Valid @RequestBody
            CreateLineRequest request
    ) {

        return lineService.createLine(
                request
        );
    }
}