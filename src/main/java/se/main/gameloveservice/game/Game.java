package se.main.gameloveservice.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import se.main.gameloveservice.player.Player;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "GAME")
@Table(name = "GLS_GAMES")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "lovedGames")
    @JsonIgnore
    private Set<Player> players;

    private int loves;

    public void gotLoved() {
        loves++;
    }

    public void gotUnloved() {
        loves--;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (o instanceof Game game) {
            return game.id == this.id;
        }

        return false;
    }

}
