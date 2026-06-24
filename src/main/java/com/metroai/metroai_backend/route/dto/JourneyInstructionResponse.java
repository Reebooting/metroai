package com.metroai.metroai_backend.route.dto;
import java.io.Serializable;

public record JourneyInstructionResponse(

        Integer stepNumber,

        String instruction

) implements Serializable {
}