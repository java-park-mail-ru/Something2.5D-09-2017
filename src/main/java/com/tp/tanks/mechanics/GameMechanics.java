package com.tp.tanks.mechanics;

import com.tp.tanks.mechanics.base.TankSnap;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public interface GameMechanics {

    void addTankSnapshot(@NotNull Long userId, @NotNull TankSnap clientSnap);

    void addUser(@NotNull Long userId);

    void gmStep(long frameTime);

    void getNewSpawnPoint(@NotNull Long userId);
}
