package org.mock.MockTestingProject01.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mock.MockTestingProject01.DataProvider;
import org.mock.MockTestingProject01.persistence.entity.Player;
import org.mock.MockTestingProject01.persistence.repository.PlayerRepositoryImpl;
import org.mock.MockTestingProject01.persistence.service.PlayerServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PlayerServiceImplTest_04 {

    @Mock
    private PlayerRepositoryImpl playerRepository;
    @InjectMocks
    private PlayerServiceImpl playerService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
//        when
        when(playerRepository.findAll()).thenReturn(DataProvider.playersListMock());
        List<Player> players = playerService.findAll();
//        Then
        assertNotNull(players);
        assertFalse(players.isEmpty());
        assertEquals("Lionel Messi",players.get(0).getName());
        assertEquals("Inter Miami",players.get(0).getTeam());
        assertEquals("Delantero",players.get(0).getPosition());

    }
}
