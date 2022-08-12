package se.main.gameloveservice.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import se.main.gameloveservice.game.Game;

import javax.persistence.*;
import java.util.Set;

@Builder
@Entity(name = "PLAYER")
@Table(name = "GLS_PLAYERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "GLS_PLAYERS_GAMES",
            joinColumns = {
                    @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false, updatable = false)
            })
    @JsonIgnoreProperties("players")
    private Set<Game> lovedGames;

}
