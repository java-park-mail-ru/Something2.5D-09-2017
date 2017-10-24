package com.tp.tanks.stubs;

import com.fasterxml.jackson.annotation.JsonCreator;


public class UserStub {

    private String email;
    private String password;
    private String username;

    @JsonCreator
    public UserStub() {}

    public UserStub(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static UserStub create() {
        final String username = StringGenerator.generate(10);
        String email = StringGenerator.generate(7);
        email += "@mail.ru";
        final String password = StringGenerator.generate(10);

        return new UserStub(username, email, password);
    }
}
