package com.metroai.metroai_backend.line.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.metroai.metroai_backend.exception.DuplicateResourceException;
import com.metroai.metroai_backend.line.dto.CreateLineRequest;
import com.metroai.metroai_backend.line.dto.LineResponse;
import com.metroai.metroai_backend.line.entity.Line;
import com.metroai.metroai_backend.line.repository.LineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LineService {

    private final LineRepository lineRepository;

    public LineResponse createLine(
            CreateLineRequest request
    ) {

        if (lineRepository.existsByCode(
                request.code()
        )) {

            throw new DuplicateResourceException(
                    "Line code already exists"
            );
        }

        Line line = Line.builder()
                .code(request.code())
                .name(request.name())
                .color(request.color())
                .createdAt(LocalDateTime.now())
                .build();

        Line savedLine
                = lineRepository.save(line);

        return new LineResponse(
                savedLine.getId(),
                savedLine.getCode(),
                savedLine.getName(),
                savedLine.getColor()
        );
    }
}
