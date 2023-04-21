package com.provinciaSeguros.application.repository;

import com.provinciaSeguros.application.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDataRepository extends JpaRepository<WeatherData,Long> {



}
