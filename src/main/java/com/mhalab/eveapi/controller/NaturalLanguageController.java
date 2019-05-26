package com.mhalab.eveapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhalab.eveapi.model.Message;
import com.mhalab.eveapi.model.weather.WeatherResponse;
import com.mhalab.eveapi.service.ActuatorService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ActuatorService actuatorService;

    @PostMapping("parse")
    public Message parse(@RequestBody Message input) {
        return new Message(actuatorService.act(input.getMessage()));
    }

    @PostMapping("echo")
    public String echo(@RequestBody String input) {
        return input;
    }
}
