package com.tp.tanks.game;

import com.tp.tanks.mechanics.GameMechanics;
import com.tp.tanks.mechanics.GameMechanicsImpl;
import com.tp.tanks.mechanics.MechanicsExecutor;
import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.internal.TankSnapshotService;
import com.tp.tanks.mocks.MockServerSnapshotService;
import com.tp.tanks.factories.Generators;
import com.tp.tanks.factories.TankSnapFactory;
import com.tp.tanks.websocket.RemotePointService;
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


@SuppressWarnings({"MagicNumber", "NullableProblems", "SpringJavaAutowiredMembersInspection"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ServerSnapshotServiceTest {

    @Autowired
    private MechanicsExecutor mechanicsExecutor;

    @Autowired
    private RemotePointService remotePointService;

    @Autowired
    private TankSnapshotService tankSnapshotsService;

    private GameMechanics gameMechanics;

    private MockServerSnapshotService mockServerSnapshotService;

    @Before
    public void setUp() {
        mechanicsExecutor.lock();
        mockServerSnapshotService = new MockServerSnapshotService(remotePointService);
        gameMechanics = new GameMechanicsImpl(tankSnapshotsService, mockServerSnapshotService);
    }

    @After
    public void tearDown() {
        mechanicsExecutor.unlock();
    }

    @Test
    public void numbSendSnapshotsMustBeOne() {

        final Long userId = Generators.generateLong();
        final int amount = 10;
        final List<TankSnap> tankSnapsForUser = TankSnapFactory.createManyForUser(userId, amount);


        for (TankSnap snap: tankSnapsForUser) {
            gameMechanics.addTankSnapshot(userId, snap);
        }

        gameMechanics.gmStep(1000);
        Assert.assertEquals(1,  mockServerSnapshotService.getNumbSendSnapshots());
    }

    @Test
    public void numbSendSnapshotsMustBeZero() {
        gameMechanics.gmStep(1000);
        Assert.assertEquals(0,  mockServerSnapshotService.getNumbSendSnapshots());
    }
}
