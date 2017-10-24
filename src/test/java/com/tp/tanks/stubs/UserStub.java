package com.tp.tanks.stubs;

public class UserStub {

    private String email;
    private String password;
    private String username;

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

    public static UserStub create() {
        final String username = StringGenerator.generate(10);
        String email = StringGenerator.generate(7);
        email += "@mail.ru";
        final String password = StringGenerator.generate(10);

        return new UserStub(username, email, password);
    }
}
