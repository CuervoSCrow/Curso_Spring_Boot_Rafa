package org.mock.MockTestingProject01.persistence.repository;

import org.mock.MockTestingProject01.persistence.entity.Player;

import java.util.List;

public interface IPlayerRepository {
    List<Player> findAll();
    Player findById(Long id);
    void save(Player player);
    void deleteById(Long id);
}
