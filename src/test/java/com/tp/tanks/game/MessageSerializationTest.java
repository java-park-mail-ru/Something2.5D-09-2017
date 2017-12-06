package com.tp.tanks.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.tanks.mechanics.base.ServerSnap;
import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.factories.Generators;
import com.tp.tanks.factories.ServerSnapFactory;
import com.tp.tanks.factories.TankSnapFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class MessageSerializationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final double DELTA = 1e-15;

    @Test
    public void tankSnapTest() throws IOException {

        final Long userId = Generators.generateLong();
        final TankSnap snap = TankSnapFactory.createOneForUser(userId);
        final String tankSnapString = objectMapper.writeValueAsString(snap);
        final TankSnap mappedSnap = objectMapper.readValue(tankSnapString, TankSnap.class);

        Assert.assertEquals(snap.getUserId(), mappedSnap.getUserId());
        Assert.assertEquals(snap.getPlatform().getValX(), mappedSnap.getPlatform().getValX(), DELTA);
        Assert.assertEquals(snap.getPlatform().getValY(), mappedSnap.getPlatform().getValY(), DELTA);
        Assert.assertEquals(snap.getTurretAngle(), mappedSnap.getTurretAngle(), DELTA);
        Assert.assertEquals(snap.getPlatformAngle(), mappedSnap.getPlatformAngle(), DELTA);
        Assert.assertEquals(snap.getUsername(), mappedSnap.getUsername());
    }

    @Test
    public void serverSnapTest() throws IOException {
        final ServerSnap snap = ServerSnapFactory.create();
        final String serverSnapString = objectMapper.writeValueAsString(snap);
        final ServerSnap mappedSnap = objectMapper.readValue(serverSnapString, ServerSnap.class);

        Assert.assertEquals(snap.getPlayers(), mappedSnap.getPlayers());
        Assert.assertEquals(snap.getTanks().size(), mappedSnap.getTanks().size());
    }
}
