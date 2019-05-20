package com.mhalab.eveapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhalab.eveapi.model.Message;
import com.mhalab.eveapi.model.weather.WeatherResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("nlp")
public class NaturalLanguageController {
    @PostMapping("parse")
    public Message parse(@RequestBody Message input) {
        Message output = new Message("");

        if (input.getMessage().toLowerCase().contains("pogoda")) {
            RestTemplate restTemplate = new RestTemplate();

            String url = "http://api.openweathermap.org/data/2.5/weather?q=Wrocław&units=Metric&APIkey=0c5cdd2e1c08df83b6acc3ed70734cb1";
            ResponseEntity<String> response
                    = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                output.setMessage("Błąd podczas komunikacji z serwisem pogodowym");
            } else {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    WeatherResponse weather = mapper.readValue(response.getBody(), WeatherResponse.class);
                    output.setMessage(weather.getName() + ": " + weather.getWeather().get(0).getDescription() + " " + weather.getMain().getTemp());
                } catch (IOException e) {
                    output.setMessage("Nie rozumiem odpowiedzi z serwisu pogodowego: " + e.getMessage());
                }
            }

        } else if (input.getMessage().toLowerCase().contains("godzina")) {
            output.setMessage(LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        else {
            output.setMessage("Usłyszałam \"" + input.getMessage() + "\"");
        }

        return output;
    }

    @PostMapping("echo")
    public String echo(@RequestBody String input) {
        return input;
    }
}
