package com.metroai.metroai_backend.line.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateLineRequest(

        @NotBlank
        String code,

        @NotBlank
        String name,

        @NotBlank
        String color

) {
}