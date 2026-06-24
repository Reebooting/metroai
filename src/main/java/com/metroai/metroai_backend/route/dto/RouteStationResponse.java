package com.metroai.metroai_backend.route.dto;

import java.io.Serializable;

public record RouteStationResponse(

        Long id,

        String name,

        Boolean isInterchange

) implements Serializable {
}