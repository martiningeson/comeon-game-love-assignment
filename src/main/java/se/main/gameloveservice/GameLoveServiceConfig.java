package se.main.gameloveservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.main.gameloveservice.game.Game;
import se.main.gameloveservice.game.GameRepository;
import se.main.gameloveservice.player.Player;
import se.main.gameloveservice.player.PlayerRepository;

import java.util.Set;
import java.util.stream.IntStream;

@Configuration
public class GameLoveServiceConfig {

    @Bean
    public CommandLineRunner init(PlayerRepository playerRepository, GameRepository gameRepository) {
        return args -> {
            Game game = createGameWithNrLoves("FirstGame", 1);
            Player player = Player.builder()
                    .name("FirstPlayer")
                    .lovedGames(Set.of(game))
                    .build();
            playerRepository.save(player);

            IntStream.range(2, 11)
                    .boxed()
                    .map(i -> createGameWithNrLoves("Game" + i, i))
                    .forEach(gameRepository::save);
        };
    }

    private Game createGameWithNrLoves(String name, int nrOfLoves) {
        return Game.builder()
                .name(name)
                .loves(nrOfLoves)
                .build();
    }

}
