package com.provinciaSeguros.application.repository;

import com.provinciaSeguros.application.model.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureRepository extends JpaRepository<Temperature, Long> {
}
