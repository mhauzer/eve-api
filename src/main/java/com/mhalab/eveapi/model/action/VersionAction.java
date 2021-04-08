package com.mhalab.eveapi.model.action;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VersionAction implements Action {

    @Override
    public String perform(final String input) {
        return "0.1";
    }
}
