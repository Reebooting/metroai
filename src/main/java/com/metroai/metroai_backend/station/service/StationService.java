package com.metroai.metroai_backend.station.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.metroai.metroai_backend.exception.DuplicateResourceException;
import com.metroai.metroai_backend.exception.ResourceNotFoundException;
import com.metroai.metroai_backend.station.dto.CreateStationRequest;
import com.metroai.metroai_backend.station.dto.StationResponse;
import com.metroai.metroai_backend.station.entity.Station;
import com.metroai.metroai_backend.station.repository.StationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    public StationResponse createStation(
        CreateStationRequest request
) {

    if (stationRepository.existsByCode(
            request.code()
    )) {

       throw new DuplicateResourceException(
        "Station code already exists"
);
    }

    Station station = Station.builder()
            .name(request.name())
            .code(request.code())
            .line(request.line())
            .latitude(request.latitude())
            .longitude(request.longitude())
            .isInterchange(request.isInterchange())
            .createdAt(LocalDateTime.now())
            .build();

    Station savedStation = stationRepository.save(station);

    return new StationResponse(
            savedStation.getId(),
            savedStation.getName(),
            savedStation.getCode(),
            savedStation.getLine(),
            savedStation.getLatitude(),
            savedStation.getLongitude(),
            savedStation.getIsInterchange()
    );
}

public List<StationResponse> getAllStations() {

    return stationRepository.findAll()
            .stream()
            .map(station -> new StationResponse(
                    station.getId(),
                    station.getName(),
                    station.getCode(),
                    station.getLine(),
                    station.getLatitude(),
                    station.getLongitude(),
                    station.getIsInterchange()
            ))
            .toList();
}

public StationResponse getStationById(
        Long id
) {

    Station station =
            stationRepository
                    .findById(id)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Station not found"
                            )
                    );

    return new StationResponse(
            station.getId(),
            station.getName(),
            station.getCode(),
            station.getLine(),
            station.getLatitude(),
            station.getLongitude(),
            station.getIsInterchange()
    );
}


public List<StationResponse> searchStations(
        String name
) {

    return stationRepository
            .findByNameContainingIgnoreCase(name)
            .stream()
            .map(station -> new StationResponse(
                    station.getId(),
                    station.getName(),
                    station.getCode(),
                    station.getLine(),
                    station.getLatitude(),
                    station.getLongitude(),
                    station.getIsInterchange()
            ))
            .toList();
}

public StationResponse updateStation(
        Long id,
        CreateStationRequest request
) {

    Station station =
            stationRepository
                    .findById(id)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Station not found"
                            )
                    );

    station.setName(request.name());
    station.setCode(request.code());
    station.setLine(request.line());
    station.setLatitude(request.latitude());
    station.setLongitude(request.longitude());
    station.setIsInterchange(
            request.isInterchange()
    );

    Station updatedStation =
            stationRepository.save(station);

    return new StationResponse(
            updatedStation.getId(),
            updatedStation.getName(),
            updatedStation.getCode(),
            updatedStation.getLine(),
            updatedStation.getLatitude(),
            updatedStation.getLongitude(),
            updatedStation.getIsInterchange()
    );
}

public void deleteStation(
        Long id
) {

    Station station =
            stationRepository
                    .findById(id)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Station not found"
                            )
                    );

    stationRepository.delete(
            station
    );
}

}