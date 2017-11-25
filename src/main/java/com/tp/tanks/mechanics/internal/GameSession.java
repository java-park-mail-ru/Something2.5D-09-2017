package com.tp.tanks.mechanics.internal;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameSession {

    private final ArrayList<Long> users = new ArrayList<>();

    public ArrayList<Long> getUsers() {
        return users;
    }

    public void addUser(Long userId) {
        users.add(userId);
    }
}
