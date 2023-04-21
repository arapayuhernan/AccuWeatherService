package com.provinciaSeguros.application.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.provinciaSeguros.application.model.Temperature;
import com.provinciaSeguros.application.model.WeatherData;
import com.provinciaSeguros.application.repository.TemperatureRepository;
import com.provinciaSeguros.application.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;


@Service
public class AccuWeatherServiceImpl implements AccuWeatherService{

    @Value("${accuweather.api.key}")
    private String apiKey;
    @Value("${accuweather.api.endpoint}")
    private String endpoint;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Autowired
    private TemperatureRepository temperatureRepository;





    @Override
    public WeatherData saveWeatherData(String locationKey) throws IOException {

        //aca se podria hacer una validacion de que lo que le pasemos sea uns string y no cualquier cosa
        //tambien se puede hacer un handler error con un try catch
        String url = endpoint + "/" + locationKey + "?apikey=" + apiKey;
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode data = response.getBody();


        JsonNode temperatura =  data.get(0).get("Temperature");
        Temperature temp = new Temperature();


        temp.setValue(temperatura.get("Metric").get("Value").asDouble());
        temp.setUnit(temperatura.get("Metric").get("Unit").toString());

        WeatherData weatherData = new WeatherData();
        weatherData.setDayTime(data.get(0).get("IsDayTime").asBoolean());
        weatherData.setHasPrecipitation(data.get(0).get("HasPrecipitation").asBoolean());
        weatherData.setWeatherText(data.get(0).get("WeatherText").toString());


        weatherData.setTemperature(temperatureRepository.save(temp));

        WeatherData save = weatherDataRepository.save(weatherData);
        return save;
    }


}
