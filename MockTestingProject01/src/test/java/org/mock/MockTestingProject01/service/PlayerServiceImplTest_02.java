package org.mock.MockTestingProject01.service;

import org.junit.jupiter.api.Test;
import org.mock.MockTestingProject01.DataProvider;
import org.mock.MockTestingProject01.persistence.entity.Player;
import org.mock.MockTestingProject01.persistence.repository.PlayerRepositoryImpl;
import org.mock.MockTestingProject01.persistence.service.PlayerServiceImpl;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerServiceImplTest_02 {

    @Test
    public void testFindAll() {

//        given
        PlayerRepositoryImpl playerRepository= mock(PlayerRepositoryImpl.class);
        PlayerServiceImpl playerService = new PlayerServiceImpl(playerRepository);

//        when
        when(playerRepository.findAll()).thenReturn(DataProvider.playersListMock());
        List<Player> players = playerService.findAll();

//        then
        assertNotNull(players);     // players != null
        assertFalse(players.isEmpty());     // players is not empty
        assertEquals("Lionel Messi", players.get(0).getName()); // players.get(0).getName() == "Liones Messi"
        assertEquals("Inter Miami", players.get(0).getTeam()); // players.get(0).getTeam() == "Inter Miami"
        assertEquals("Delantero", players.get(0).getPosition()); // players.get(0).getPosition() == "Delantero"

    }
}
