package com.mhalab.eveapi.model.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhalab.eveapi.model.weather.WeatherResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class WeatherAction extends RestCallAction {
    public WeatherAction(String url) {
        super(url);
    }


    @Override
    public String perform(String input) {
        String output = new String();
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://api.openweathermap.org/data/2.5/weather?q=Wrocław&units=Metric&APIkey=0c5cdd2e1c08df83b6acc3ed70734cb1";
        ResponseEntity<String> response
                = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            output = "Błąd podczas komunikacji z serwisem pogodowym";
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                WeatherResponse weather = mapper.readValue(response.getBody(), WeatherResponse.class);
                output = weather.getName() + ": " + weather.getWeather().get(0).getDescription() + " " + weather.getMain().getTemp();
            } catch (IOException e) {
                output = "Nie rozumiem odpowiedzi z serwisu pogodowego: " + e.getMessage();
            }
        }

        return output;
    }
}
