package org.mock.MockTestingProject01.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Player {
    private Long id;
    private String name;
    private String team;
    private String position;

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", team='" + team + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
