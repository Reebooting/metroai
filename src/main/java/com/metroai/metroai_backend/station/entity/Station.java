package com.metroai.metroai_backend.station.entity;

import java.time.LocalDateTime;

import com.metroai.metroai_backend.line.entity.Line;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "stations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    //private MetroLine line;
    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;
    private Double latitude;

    private Double longitude;

    private Boolean isInterchange;

    private LocalDateTime createdAt;
}