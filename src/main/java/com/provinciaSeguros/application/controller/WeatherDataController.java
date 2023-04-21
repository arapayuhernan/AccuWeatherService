package com.provinciaSeguros.application.controller;

;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.provinciaSeguros.application.model.WeatherData;
import com.provinciaSeguros.application.service.AccuWeatherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class WeatherDataController {

    @Autowired
    AccuWeatherServiceImpl accuWeatherService;



    @PostMapping(value = "weather/{locationId}")
    public ResponseEntity<WeatherData> saveWeatherData(@PathVariable String locationId) throws IOException {
         return ResponseEntity.ok(accuWeatherService.saveWeatherData(locationId));

    }
}
