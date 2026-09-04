package org.mock.MockTestingProject01.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mock.MockTestingProject01.DataProvider;
import org.mock.MockTestingProject01.persistence.entity.Player;
import org.mock.MockTestingProject01.persistence.repository.PlayerRepositoryImpl;
import org.mock.MockTestingProject01.persistence.service.PlayerServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerServiceImplTest_03 {

    private PlayerRepositoryImpl playerRepository;
    private PlayerServiceImpl playerService;

    @BeforeEach
    public void init() {
        this.playerRepository = mock(PlayerRepositoryImpl.class);
        this.playerService = new PlayerServiceImpl(playerRepository);
    }

    @Test
    public void testFindAll() {

//        Given
        PlayerRepositoryImpl playerRepository = mock(PlayerRepositoryImpl.class);
        PlayerServiceImpl playerService = new PlayerServiceImpl(playerRepository);

//        when
        when(playerRepository.findAll()).thenReturn(DataProvider.playersListMock());
        List<Player> players = playerService.findAll();

//        then
        assertNotNull(players);
        assertFalse(players.isEmpty());
        assertEquals("Lionel Messi",players.get(0).getName());
        assertEquals("Inter Miami",players.get(0).getTeam());
        assertEquals("Delantero",players.get(0).getPosition());
    }
}
