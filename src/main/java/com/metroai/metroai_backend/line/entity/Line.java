package com.metroai.metroai_backend.line.entity;
import java.time.LocalDateTime;
import java.util.List;

import com.metroai.metroai_backend.station.entity.Station;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;
    
    @OneToMany(mappedBy = "line")
private List<Station> stations;

    private LocalDateTime createdAt;
}