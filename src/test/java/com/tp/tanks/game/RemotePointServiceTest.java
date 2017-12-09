package com.tp.tanks.game;

import com.tp.tanks.factories.Generators;
import com.tp.tanks.factories.TankSnapFactory;
import com.tp.tanks.mechanics.GameMechanics;
import com.tp.tanks.mechanics.GameMechanicsImpl;
import com.tp.tanks.mechanics.MechanicsExecutor;
import com.tp.tanks.mechanics.base.ServerSnap;
import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.internal.ServerSnapshotService;
import com.tp.tanks.mechanics.internal.TankSnapshotService;
import com.tp.tanks.mocks.MockRemotePointService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;


@SuppressWarnings({"MagicNumber", "NullableProblems", "SpringJavaAutowiredMembersInspection"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class RemotePointServiceTest {

    @Autowired
    private MechanicsExecutor mechanicsExecutor;

    @Autowired
    private TankSnapshotService tankSnapshotsService;

    private GameMechanics gameMechanics;

    private MockRemotePointService mockRemotePointService;

    private static final double DELTA = 1e-15;

    @Before
    public void setUp() {
        mechanicsExecutor.lock();
        mockRemotePointService = new MockRemotePointService();
        ServerSnapshotService serverSnapshotService = new ServerSnapshotService(mockRemotePointService);
        gameMechanics = new GameMechanicsImpl(tankSnapshotsService, serverSnapshotService, mockRemotePointService);
    }

    @After
    public void tearDown() {
        mechanicsExecutor.unlock();
    }


    @Test
    public void processedOnePlayerTest() {
        final Long userId = Generators.generateLong();
        final TankSnap tankSnapForUser = TankSnapFactory.createOneForUser(userId);
        mockRemotePointService.pushUser(tankSnapForUser.getUserId());

        gameMechanics.addTankSnapshot(userId, tankSnapForUser);
        gameMechanics.gmStep(1000);


        final ServerSnap serverSnap = mockRemotePointService.getSendingServerSnap();
        final List<TankSnap> tanks = serverSnap.getTanks();
        final TankSnap tankSnap = tanks.get(0);

        checkTankSnap(tankSnapForUser, tankSnap);
        Assert.assertEquals(1, tanks.size());
    }


    @Test
    public void processedSeveralPlayerTest() {
        final Long userId1 = Generators.generateLong();
        final Long userId2 = Generators.generateLong();

        final TankSnap tankSnapForUser1 = TankSnapFactory.createOneForUser(userId1);
        final TankSnap tankSnapForUser2 = TankSnapFactory.createOneForUser(userId2);
        mockRemotePointService.pushUser(tankSnapForUser1.getUserId());
        mockRemotePointService.pushUser(tankSnapForUser2.getUserId());

        gameMechanics.addTankSnapshot(userId1, tankSnapForUser1);
        gameMechanics.addTankSnapshot(userId2, tankSnapForUser2);
        gameMechanics.gmStep(1000);

        final ServerSnap serverSnap = mockRemotePointService.getSendingServerSnap();
        final List<TankSnap> tanks = serverSnap.getTanks();

        Assert.assertEquals(2, tanks.size());

        TankSnap tank1 = tanks.get(0);
        TankSnap tank2 = tanks.get(1);

        if (!Objects.equals(tank1.getUserId(), userId1) && !Objects.equals(tank2.getUserId(), userId2)) {
            final TankSnap tmp = tank1;
            tank1 = tank2;
            tank2 = tmp;
        }

        checkTankSnap(tankSnapForUser1, tank1);
        checkTankSnap(tankSnapForUser2, tank2);
    }


    @Test
    public void processedZeroPlayerTest() {
        gameMechanics.gmStep(1000);
        final ServerSnap serverSnap = mockRemotePointService.getSendingServerSnap();
        Assert.assertNull(serverSnap);
    }


    private void checkTankSnap(TankSnap expected, TankSnap actual) {
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getPlatform().getValX(), actual.getPlatform().getValX(), DELTA);
        Assert.assertEquals(expected.getPlatform().getValY(), actual.getPlatform().getValY(), DELTA);
        Assert.assertEquals(expected.getPlatformAngle(), actual.getPlatformAngle(), DELTA);
        Assert.assertEquals(expected.getTurretAngle(), actual.getTurretAngle(), DELTA);
    }
}
