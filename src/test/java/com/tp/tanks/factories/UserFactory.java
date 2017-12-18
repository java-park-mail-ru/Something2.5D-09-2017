package com.tp.tanks.factories;

import com.tp.tanks.models.User;

public class UserFactory {

    public static User create() {
        final Long id = null;
        final String username = Generators.generateString(10);
        String email = Generators.generateString(7);
        email += "@mail.ru";
        final String password = Generators.generateString(10);
        final Boolean mouseControlEnabled = Generators.generateBoolean();

        return new User(id, username, email, password, mouseControlEnabled);
    }
}
