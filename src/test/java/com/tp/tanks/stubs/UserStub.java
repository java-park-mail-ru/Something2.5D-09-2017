package com.tp.tanks.stubs;

import com.tp.tanks.model.User;

public class UserStub {
    public static User create() {
        final Long id = null;
        final String username = StringGenerator.generate(10);
        String email = StringGenerator.generate(7);
        email += "@mail.ru";
        final String password = StringGenerator.generate(10);

        return new User(id, username, email, password);
    }
}
