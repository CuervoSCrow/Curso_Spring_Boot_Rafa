package org.mock.MockTestingProject01.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mock.MockTestingProject01.DataProvider;
import org.mock.MockTestingProject01.persistence.entity.Player;
import org.mock.MockTestingProject01.persistence.repository.PlayerRepositoryImpl;
import org.mock.MockTestingProject01.persistence.service.PlayerServiceImpl;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplTest_01 {
    @Mock
    private PlayerRepositoryImpl playerRepository;
    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    public void testFindAll() {

//        When
        when(playerRepository.findAll()).thenReturn(DataProvider.playersListMock());
        List<Player> players =playerService.findAll();

//        Then
        assertNotNull(players);
        assertFalse(players.isEmpty());
        assertEquals("Lionel Messi",players.get(0).getName());
        assertEquals("Inter Miami",players.get(0).getTeam());
        assertEquals("Delantero",players.get(0).getPosition());
    }

    @Test
    public void testFindById() {
//        Given
        Long id = 1L;
//        when
        when(this.playerRepository.findById(anyLong()))
                .thenReturn(DataProvider.playerMock());
        Player player = this.playerService.findById(id);

//        Then
        assertNotNull(player);
        assertEquals("Lionel Messi", player.getName());
        assertEquals("Inter Miami", player.getTeam());
        assertEquals("Delantero", player.getPosition());
        verify(this.playerRepository,times(1)).findById(anyLong());
    }

    @Test
    public void testSave(){
//            Given
        Player player = DataProvider.newPlayerMock();
//            when
        this.playerService.save(player);
//            then
        ArgumentCaptor<Player> playerArgumentCaptor =
                ArgumentCaptor.forClass(Player.class);
        verify(this.playerRepository).save(any(Player.class));
        verify(this.playerRepository).save(playerArgumentCaptor.capture());
        assertEquals(10L, playerArgumentCaptor.getValue().getId());
        assertEquals("Luis Diaz", playerArgumentCaptor.getValue().getName());
        assertEquals("Liverpool", playerArgumentCaptor.getValue().getTeam());
        assertEquals("Delantero", playerArgumentCaptor.getValue().getPosition());
    }

    @Test
    public void testDeleteById(){
//        given
        Long id = 1L;
//        when
        this.playerService.deleteById(id);

//        then
        ArgumentCaptor<Long> longArgumentCaptor =
                ArgumentCaptor.forClass(Long.class);
        verify(this.playerRepository).deleteById(anyLong());
        verify(this.playerRepository).deleteById(longArgumentCaptor.capture());
        assertEquals(1L,longArgumentCaptor.getValue());
    }
}
