package com.tp.tanks.stubs;

import com.tp.tanks.mechanics.base.TankSnap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TankSnapFactory {

    public static List<TankSnap> createManyForUser(Long userId, int amount) {
        List<TankSnap> tanksSnaps = new ArrayList<>();
        for(int i = 0; i < amount; ++i) {
            tanksSnaps.add(createOneForUser(userId));
        }
        return tanksSnaps;
    }

    public static TankSnap createOneForUser(Long userId) {
        double startVal = 0;
        double endVal = 10000;

        TankSnap tankSnap = new TankSnap();

        tankSnap.setPlatform(CoordinateFactory.create());

        double randomDouble = new Random().nextDouble();
        double val = startVal + (randomDouble * (endVal - startVal));
        tankSnap.setPlatformAngle(val);

        randomDouble = new Random().nextDouble();
        val = startVal + (randomDouble * (endVal - startVal));
        tankSnap.setTurretAngle(val);

        String username = StringGenerator.generate(15);
        tankSnap.setUsername(username);

        Boolean randomBoolean = new Random().nextBoolean();
        tankSnap.setShoot(randomBoolean);

        tankSnap.setUserId(userId);
        return tankSnap;
    }
}
