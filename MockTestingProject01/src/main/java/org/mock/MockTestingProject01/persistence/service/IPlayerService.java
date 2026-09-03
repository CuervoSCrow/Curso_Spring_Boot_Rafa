package org.mock.MockTestingProject01.persistence.service;

import org.mock.MockTestingProject01.persistence.entity.Player;

import java.util.List;

public interface IPlayerService {
    List<Player> findAll();
    Player findById(Long id);
    void save(Player player);
    void deleteById(Long id);
}
