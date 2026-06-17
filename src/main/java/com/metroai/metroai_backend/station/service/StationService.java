package com.metroai.metroai_backend.station.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.metroai.metroai_backend.exception.DuplicateResourceException;
import com.metroai.metroai_backend.exception.ResourceNotFoundException;
import com.metroai.metroai_backend.linestation.entity.LineStation;
import com.metroai.metroai_backend.linestation.repository.LineStationRepository;
import com.metroai.metroai_backend.station.dto.CreateStationRequest;
import com.metroai.metroai_backend.station.dto.NearestStationResponse;
import com.metroai.metroai_backend.station.dto.StationDetailsResponse;
import com.metroai.metroai_backend.station.dto.StationResponse;
import com.metroai.metroai_backend.station.entity.Station;
import com.metroai.metroai_backend.station.repository.StationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;
    private final LineStationRepository lineStationRepository;
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
            station.getLatitude(),
            station.getLongitude(),
            station.getIsInterchange()
)).toList();
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

public StationDetailsResponse getStationDetails(
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

    LineStation lineStation =
            lineStationRepository
                    .findFirstByStationId(id)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Station not assigned to line"
                            )
                    );

    return new StationDetailsResponse(
            station.getId(),
            station.getName(),
            station.getCode(),
            lineStation.getStationOrder(),
            lineStation.getDistanceFromStart(),
            station.getLatitude(),
            station.getLongitude(),
            station.getIsInterchange()
    );
}

public NearestStationResponse findNearestStation(
        Double latitude,
        Double longitude
) {

    List<Station> stations =
            stationRepository.findAll();

    Station nearestStation = null;

    double minimumDistance =
            Double.MAX_VALUE;

    for (Station station : stations) {

        if (
                station.getLatitude() == null
                ||
                station.getLongitude() == null
        ) {
            continue;
        }

        double distance =
                calculateDistance(
                        latitude,
                        longitude,
                        station.getLatitude(),
                        station.getLongitude()
                );

        if (distance < minimumDistance) {

            minimumDistance = distance;
            nearestStation = station;
        }
    }

    if (nearestStation == null) {

        throw new ResourceNotFoundException(
                "No stations found"
        );
    }

    return new NearestStationResponse(
            nearestStation.getId(),
            nearestStation.getName(),
            Math.round(
                    minimumDistance * 100.0
            ) / 100.0
    );
}

private double calculateDistance(

        double lat1,

        double lon1,

        double lat2,

        double lon2

) {

    final double EARTH_RADIUS = 6371;

    double dLat =
            Math.toRadians(
                    lat2 - lat1
            );

    double dLon =
            Math.toRadians(
                    lon2 - lon1
            );

    double a =
            Math.sin(dLat / 2)
                    * Math.sin(dLat / 2)
                    +
                    Math.cos(
                            Math.toRadians(lat1)
                    )
                    *
                    Math.cos(
                            Math.toRadians(lat2)
                    )
                    *
                    Math.sin(dLon / 2)
                    *
                    Math.sin(dLon / 2);

    double c =
            2
                    *
                    Math.atan2(
                            Math.sqrt(a),
                            Math.sqrt(1 - a)
                    );

    return EARTH_RADIUS * c;
}

}