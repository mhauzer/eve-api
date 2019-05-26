package com.mhalab.eveapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhalab.eveapi.model.Skill;
import com.mhalab.eveapi.model.action.*;
import com.mhalab.eveapi.model.weather.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jca.work.SimpleTaskWorkManager;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class ActuatorService {
    private Map<Skill, Action> skills = new HashMap<Skill, Action>();
    private Map<String, Skill> wordmap = new HashMap<>();

    @Autowired
    private NlpService nlpService;

    @Autowired
    private WeatherService weatherService;

    public ActuatorService() {
        skills.put(Skill.WEATHER, new WeatherAction("http://api.openweathermap.org/data/2.5/weather?q=Wrocław&units=Metric&APIkey=0c5cdd2e1c08df83b6acc3ed70734cb1"));
        skills.put(Skill.TIME, new TimeAction());
        skills.put(Skill.DATE, new DateAction());
        skills.put(Skill.ECHO, new EchoAction());
        skills.put(Skill.TASK, new TaskManagerAction(""));

        wordmap.put("pogoda", Skill.WEATHER);
        wordmap.put("aura", Skill.WEATHER);
        wordmap.put("godzina", Skill.TIME);
        wordmap.put("czas", Skill.TIME);
        wordmap.put("dzień", Skill.DATE);
        wordmap.put("data", Skill.DATE);
        wordmap.put("zadanie", Skill.TASK);
    }

    private Skill understand(final String input) {
        String s = input.toLowerCase();
        Skill skill = wordmap.get(s);

        if (skill == null) {
            skill = Skill.ECHO;
        }
        return skill;
    }

    public String act(final String input) {
        Skill skill = understand(input);

        return skills.get(skill).perform(input);
    }
}
