package com.provinciaSeguros.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provinciaSeguros.application.model.WeatherData;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface AccuWeatherService {


    public WeatherData saveWeatherData(String idLocation) throws IOException;
}
