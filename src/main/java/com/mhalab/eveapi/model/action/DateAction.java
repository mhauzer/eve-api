package com.mhalab.eveapi.model.action;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateAction implements Action {
    @Override
    public String perform(final String input) {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
