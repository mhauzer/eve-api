package com.mhalab.eveapi.model.action;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeAction implements Action {

    @Override
    public String perform(final String input) {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
