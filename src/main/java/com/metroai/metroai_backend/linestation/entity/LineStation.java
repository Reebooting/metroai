package com.metroai.metroai_backend.linestation.entity;

import java.time.LocalDateTime;

import com.metroai.metroai_backend.line.entity.Line;
import com.metroai.metroai_backend.station.entity.Station;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "line_stations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "line_id",
            nullable = false
    )
    private Line line;

    @ManyToOne
    @JoinColumn(
            name = "station_id",
            nullable = false
    )
    private Station station;

    @Column(nullable = false)
    private Integer stationOrder;

    private Double distanceFromStart;

    private LocalDateTime createdAt;
}