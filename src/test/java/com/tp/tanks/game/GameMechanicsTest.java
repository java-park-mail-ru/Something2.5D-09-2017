package com.tp.tanks.game;

import com.tp.tanks.mechanics.GameMechanics;
import com.tp.tanks.mechanics.GameMechanicsImpl;
import com.tp.tanks.mechanics.MechanicsExecutor;
import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.internal.TankSnapshotService;
import com.tp.tanks.mocks.MockServerSnapshotService;
import com.tp.tanks.stubs.Generators;
import com.tp.tanks.stubs.TankSnapFactory;
import com.tp.tanks.websocket.RemotePointService;
import org.jetbrains.annotations.NotNull;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;


@SuppressWarnings({"MagicNumber", "NullableProblems", "SpringJavaAutowiredMembersInspection"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class GameMechanicsTest {

    @Autowired
    private TankSnapshotService tankSnapshotsService;

    @NotNull
    private GameMechanics gameMechanics;

    @Autowired
    private MechanicsExecutor mechanicsExecutor;

    @Autowired
    private RemotePointService remotePointService;

    private MockServerSnapshotService mockServerSnapshotService;

//    @MockBean
//    private ServerSnapshotService mockServerSnapshotService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameMechanicsTest.class);

    @Captor
    private ArgumentCaptor<ArrayList<TankSnap>> captor;

    private ArgumentCaptor<List<TankSnap>> argumentCaptor;


    @Before
    public void before () {

        mechanicsExecutor.lock();
//        final Class<List<TankSnap>> cls = (Class<List<TankSnap>>)(Object)List.class;
//        mockServerSnapshotService = mock(MockServerSnapshotService.class);
//
//        doAnswer((Answer<Object>) invocation -> {
//            List<TankSnap> arg = invocation.getArgumentAt(0, cls);
//            MockServerSnapshotService mock = (MockServerSnapshotService) invocation.getMock();
//            mock.setProcessedSnapshots(arg.size());
//            return null;
//        }).when(mockServerSnapshotService).send(any());
//
//        when(mockServerSnapshotService.getNumbProcessedSnapshots()).thenAnswer((Answer<Integer>) invocation -> {
//            MockServerSnapshotService mock = (MockServerSnapshotService) invocation.getMock();
//            return mock.getProcessedSnapshots1();
//        });
//
//        gameMechanics = new GameMechanicsImpl(tankSnapshotsService, mockServerSnapshotService);

        mockServerSnapshotService = new MockServerSnapshotService(remotePointService);
        gameMechanics = new GameMechanicsImpl(tankSnapshotsService, mockServerSnapshotService);
    }

    @After
    public void after() {
        mechanicsExecutor.unlock();
    }

    @Test
    public void numbProcessedSnapshotsMustBeOne() {
        Long userId1 = Generators.generateLong();
        int amount1 = 10;
        List<TankSnap> tankSnapsForUser1 = TankSnapFactory.createManyForUser(userId1, amount1);


        for (TankSnap snap: tankSnapsForUser1) {
            gameMechanics.addTankSnapshot(userId1, snap);
        }

        gameMechanics.gmStep(1000);
        Assert.assertEquals(1,  mockServerSnapshotService.getNumbProcessedSnapshots());
    }


    public void numbProcessedSnapshotsMustBeZero() {
        gameMechanics.gmStep(1000);
        Assert.assertEquals(0,  mockServerSnapshotService.getNumbProcessedSnapshots());
    }
}
