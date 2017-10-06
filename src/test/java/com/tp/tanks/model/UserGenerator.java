package com.tp.tanks.model;

public class UserGenerator {
    public static User generateUser() {
        final Long id = null;
        final String username = StringGenerator.generateString(10);
        String email = StringGenerator.generateString(7);
        email += "@mail.ru";
        final String password = StringGenerator.generateString(10);

        return new User(id, username, email, password);
    }
}
