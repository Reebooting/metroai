package com.metroai.metroai_backend.station.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.metroai.metroai_backend.station.dto.CreateStationRequest;
import com.metroai.metroai_backend.station.dto.NearestStationResponse;
import com.metroai.metroai_backend.station.dto.StationDetailsResponse;
import com.metroai.metroai_backend.station.dto.StationResponse;
import com.metroai.metroai_backend.station.service.StationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @PostMapping
    public StationResponse createStation(
            @Valid @RequestBody
            CreateStationRequest request
    ) {

        return stationService.createStation(
                request
        );
    }

@GetMapping
public List<StationResponse> getAllStations() {

    return stationService.getAllStations();
}

@GetMapping("/{id}")
public StationResponse getStationById(
        @PathVariable Long id
) {

    return stationService.getStationById(
            id
    );
}
@GetMapping("/nearest")
public NearestStationResponse nearestStation(

        @RequestParam Double latitude,

        @RequestParam Double longitude

) {

    return stationService.findNearestStation(
            latitude,
            longitude
    );
}

@GetMapping("/search")
public List<StationResponse> searchStations(
        @RequestParam String name
) {

    return stationService.searchStations(
            name
    );
}

@PutMapping("/{id}")
public StationResponse updateStation(
        @PathVariable Long id,
        @Valid @RequestBody
        CreateStationRequest request
) {

    return stationService.updateStation(
            id,
            request
    );
}

@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteStation(
        @PathVariable Long id
) {

    stationService.deleteStation(
            id
    );
}

@GetMapping("/{id}/details")
public StationDetailsResponse getStationDetails(
        @PathVariable Long id
) {

    return stationService.getStationDetails(
            id
    );
}

}