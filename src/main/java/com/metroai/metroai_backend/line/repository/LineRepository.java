package com.metroai.metroai_backend.line.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metroai.metroai_backend.line.entity.Line;

public interface LineRepository
        extends JpaRepository<Line, Long> {

    boolean existsByCode(String code);

    Optional<Line> findByCode(String code);
}