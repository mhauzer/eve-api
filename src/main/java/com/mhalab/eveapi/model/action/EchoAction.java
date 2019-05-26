package com.mhalab.eveapi.model.action;

public class EchoAction implements Action {
    @Override
    public String perform(final String input) {
        return "Usłyszałam \"" + input + "\"";
    }
}
