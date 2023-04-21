package com.provinciaSeguros.application;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.provinciaSeguros.application.model.Temperature;
import com.provinciaSeguros.application.model.WeatherData;
import com.provinciaSeguros.application.repository.TemperatureRepository;
import com.provinciaSeguros.application.repository.WeatherDataRepository;
import com.provinciaSeguros.application.service.AccuWeatherServiceImpl;
import org.json.JSONException;


import org.junit.Test;

import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.mockito.MockitoAnnotations;


import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@TestPropertySource(properties = {
        "accuweather.api.key",
        "accuweather.api.endpoint"
})
@RunWith(MockitoJUnitRunner.class)
public class WeatherTest {



    private final String WEATHER = "{\n" +
            "    \"weatherText\": \"\\\"Cloudy\\\"\",\n" +
            "    \"dayTime\": true,\n" +
            "    \"temperature\": {\n" +
            "        \"id\": 1,\n" +
            "        \"value\": 16.4,\n" +
            "        \"unit\": \"\\\"C\\\"\"\n" +
            "    },\n" +
            "    \"hasPrecipitation\": false\n" +
            "}";

    private final String ID_LOCATION = "1234";


    @Mock
    private WeatherDataRepository weatherDataRepository;

    @Mock
    private TemperatureRepository temperatureRepository;

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private AccuWeatherServiceImpl accuWeatherService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveWeather() throws IOException {


        String RESPONSE = "[\n" +
                "  {\n" +
                "    \"LocalObservationDateTime\": \"2023-04-21T13:05:00+10:00\",\n" +
                "    \"EpochTime\": 1682046300,\n" +
                "    \"WeatherText\": \"Cloudy\",\n" +
                "    \"WeatherIcon\": 7,\n" +
                "    \"HasPrecipitation\": false,\n" +
                "    \"PrecipitationType\": null,\n" +
                "    \"IsDayTime\": true,\n" +
                "    \"Temperature\": {\n" +
                "      \"Metric\": {\n" +
                "        \"Value\": 16.4,\n" +
                "        \"Unit\": \"C\",\n" +
                "        \"UnitType\": 17\n" +
                "      },\n" +
                "      \"Imperial\": {\n" +
                "        \"Value\": 62,\n" +
                "        \"Unit\": \"F\",\n" +
                "        \"UnitType\": 18\n" +
                "      }\n" +
                "    },\n" +
                "    \"MobileLink\": \"http://www.accuweather.com/en/au/wattle-grove/1661/current-weather/1661?lang=en-us\",\n" +
                "    \"Link\": \"http://www.accuweather.com/en/au/wattle-grove/1661/current-weather/1661?lang=en-us\"\n" +
                "  }\n" +
                "]";


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode data =  objectMapper.readValue(WEATHER,JsonNode.class);

        WeatherData weatherData = new WeatherData();
        Temperature temp =  new Temperature();
        temp.setUnit(data.get("temperature").get("unit").toString());
        temp.setValue(data.get("temperature").get("value").asDouble());
        temp.setId(1L);


        weatherData.setTemperature(temp);
        weatherData.setWeatherText(data.get("weatherText").toString());
        weatherData.setDayTime(false);
        weatherData.setHasPrecipitation(false);
        weatherData.setId(1L);

        JsonNode mockNode = objectMapper.readValue(RESPONSE,JsonNode.class);



        when(weatherDataRepository.save(any())).thenReturn(weatherData);
        when(restTemplate.getForEntity(anyString(),any())).thenReturn(new ResponseEntity<>(mockNode,HttpStatus.OK));
        when(accuWeatherService.saveWeatherData(ID_LOCATION)).thenReturn(weatherData);



        WeatherData weatherData1 = accuWeatherService.saveWeatherData(ID_LOCATION);

        assertEquals(weatherData1,weatherData1);


    }

}
