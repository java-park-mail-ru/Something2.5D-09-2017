package com.tp.tanks.model;

public class UserGenerator {
    public static User generateUser() {
        Long id = null;
        String username = StringGenerator.generateString(10);
        String email = StringGenerator.generateString(7);
        email += "@mail.ru";
        String password = StringGenerator.generateString(10);

        return new User(id, username, email, password);
    }
}
