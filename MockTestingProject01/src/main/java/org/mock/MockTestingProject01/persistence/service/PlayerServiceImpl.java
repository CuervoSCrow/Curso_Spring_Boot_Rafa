package org.mock.MockTestingProject01.persistence.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mock.MockTestingProject01.persistence.entity.Player;
import org.mock.MockTestingProject01.persistence.repository.PlayerRepositoryImpl;

import java.util.List;

@AllArgsConstructor
public class PlayerServiceImpl implements  IPlayerService{

    private PlayerRepositoryImpl playerRepository;
//
//    public PlayerServiceImpl(PlayerRepositoryImpl playerRepository) {
//        this.playerRepository = playerRepository;
//    }

    @Override
    public List<Player> findAll() {
        return this.playerRepository.findAll();
    }

    @Override
    public Player findById(Long id) {
        return this.playerRepository.findById(id);
    }

    @Override
    public void save(Player player) {
        this.playerRepository.save(player);
    }

    @Override
    public void deleteById(Long id) {
        this.playerRepository.deleteById(id);

    }
}
