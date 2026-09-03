package org.mock.MockTestingProject01.persistence.repository;

import org.mock.MockTestingProject01.persistence.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepository implements IPlayerRepository{
    private List<Player> playerDB = new ArrayList<>(
            List.of(
                    new Player(1L,"Lionel Messi", "Inter Miami", "Delantero"),
                    new Player(2L,"Cristiano Ronaldo", "Al Nassr", "Delantero"),
                    new Player(3L,"Neymar Jr","Paris Saint-Germain","Delantero"),
                    new Player(4L,"Kylian Mbappé","Real Madrid","Delantero"),
                    new Player(5L,"Kevin De Bruyne","Manchester City","Volante"),
                    new Player(6L,"Virgil van Dijk","Liverpool","Defensa")
            )
    );

    @Override
    public List<Player> findAll() {
        return List.of();
    }

    @Override
    public Player findById(Long id) {
        return null;
    }

    @Override
    public void save(Player player) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
