package com.tp.tanks.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonCreator {

    public static String generateJson(User user) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(user);
//            String pass = user.getPassword();
            json = ow.writeValueAsString(user);
//            json = ow.writeValueAsString(pass);
        } catch(JsonProcessingException e) {

        }
        return json;
    }
}
