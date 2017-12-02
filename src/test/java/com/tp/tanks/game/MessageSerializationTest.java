package com.tp.tanks.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.tanks.mechanics.base.Coordinate;
import com.tp.tanks.mechanics.base.ServerSnap;
import com.tp.tanks.stubs.TankSnapFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

public class MessageSerializationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SuppressWarnings("OverlyBroadThrowsClause")
    @Test
    public void tankSnapTest() throws IOException {


        final String coordinateSnapStr =
                '{'
                        + "platform: " + platform.toString()
                        + ", platformAngle: "  + platformAngle
                        + ", turretAngle: " + turretAngle
                        + ", isShoot: " + isShoot
                        + ", userId: " + userId.toString()
                        + ", username: " + username
                  + '}';

        final Coordinate tankSnap= objectMapper.readValue(coordinateSnapStr, Coordinate.class);
        final String clientSnapJson = objectMapper.writeValueAsString(tankSnap);
        Assert.assertNotNull(clientSnapJson);
    }

    @SuppressWarnings("OverlyBroadThrowsClause")
    @Test
    public void serverSnapSnapTest() throws IOException {
        final ServerSnap serverSnap = new ServerSnap();

        Long user_id = new Random().nextLong();
        int amountOfTanks = 20;
        serverSnap.setTanks(TankSnapFactory.createManyForUser(user_id, amountOfTanks));


        final String result = objectMapper.writeValueAsString(serverPlayerSnap);
        objectMapper.readValue(result, GameUser.ServerPlayerSnap.class);
    }




















