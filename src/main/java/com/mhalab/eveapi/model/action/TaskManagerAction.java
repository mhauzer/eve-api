package com.mhalab.eveapi.model.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhalab.eveapi.model.taskmanager.Task;
import com.mhalab.eveapi.model.weather.WeatherResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

public class TaskManagerAction extends RestCallAction {
    private String jwt = new String("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNTU4Nzc1NzkxfQ.Tza8XEcMzx0AXCankN39eZl8YK5Lq104CjFld53W8oiRELEZqUnBnR9KMGds2yEZSgCYgta-qUMwXubthluhfA");

    public TaskManagerAction(String url) {
        super(url);
    }

//    @Override
//    public String perform(String input) {
//
//        String output = new String();
//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<String> response
//                = restTemplate.getForEntity(url, String.class);
//
//        if (response.getStatusCode() != HttpStatus.OK) {
//            output = "Błąd podczas komunikacji z serwisem do zarządzania zadaniami";
//        } else {
//            ObjectMapper mapper = new ObjectMapper();
//            try {
//                Task task = mapper.readValue(response.getBody(), Task.class);
//
//                output = task.getDescription();
//            } catch (IOException e) {
//                output = "Nie rozumiem odpowiedzi z serwisu do zarządzania zadaniami: " + e.getMessage();
//            }
//        }
//
//        return output;
//
//    }

    @Override
    public String perform(String input) {

        String output = new String();

        Traverson traverson = new Traverson(URI.create(url), MediaTypes.HAL_JSON);
        Traverson.TraversalBuilder tb = traverson.follow("tasks");
        ParameterizedTypeReference<Resources<Task>> typeRefDevices = new ParameterizedTypeReference<Resources<Task>>() {};
        Resources<Task> resTasks = tb.toObject(typeRefDevices);
        Collection<Task> tasks = resTasks.getContent();

        output = tasks.stream().findFirst().get().getDescription();

        return output;

    }

}
