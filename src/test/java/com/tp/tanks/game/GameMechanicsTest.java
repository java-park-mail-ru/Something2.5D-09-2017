package com.tp.tanks.game;

import com.tp.tanks.mechanics.GameMechanics;
import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.internal.ServerSnapshotService;
import com.tp.tanks.mechanics.internal.TankSnapshotService;
import com.tp.tanks.stubs.Generators;
import com.tp.tanks.stubs.TankSnapFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@SuppressWarnings({"MagicNumber", "NullableProblems", "SpringJavaAutowiredMembersInspection"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class GameMechanicsTest {

    @Autowired
    private GameMechanics gameMechanics;

    @MockBean
    private ServerSnapshotService mockServerSnapshotService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameMechanicsTest.class);

    @Captor
    private ArgumentCaptor<ArrayList<TankSnap>> captor;

    @Before
    public void setUp () {
//        MockitoAnnotations.initMocks(this);
    LOGGER.info("[GameMechanicsTest: setUp]");
//        mockServerSnapshotService = mock(ServerSnapshotService.class);


//        verify(mockServerSnapshotService).send(captor.capture());


//        doReturn("hello").when(mockServerSnapshotService).send(anyListOf(TankSnap.class));
    }

    @Test
    public void tankSnapshotsServiceProcessSnapshotsTest() {
        Long userId1 = Generators.generateLong();
        Long userId2 = Generators.generateLong();
        int amount1 = 10;
        int amount2 = 10;

        List<TankSnap> tankSnapsForUser1 = TankSnapFactory.createManyForUser(userId1, amount1);
        List<TankSnap> tankSnapsForUser2 = TankSnapFactory.createManyForUser(userId2, amount2);

        for (TankSnap snap: tankSnapsForUser1) {
            gameMechanics.addTankSnapshot(userId1, snap);
        }
        for (TankSnap snap: tankSnapsForUser2) {
            gameMechanics.addTankSnapshot(userId2, snap);
        }


        gameMechanics.gmStep(1000);
//        List<TankSnap> test = captor.getValue();
//        LOGGER.info("test size: " + test.size());
    }
}
