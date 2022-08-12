package se.main.gameloveservice.player;

import org.springframework.stereotype.Service;
import se.main.gameloveservice.PlayerService;
import se.main.gameloveservice.exception.PlayerNotFoundException;
import se.main.gameloveservice.game.Game;

import java.util.HashSet;
import java.util.Set;

@Service
class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player createPlayer(PlayerDto playerDto) {
        return playerRepository.findByName(playerDto.getName())
                .orElseGet(() -> {
                    var newPlayer = Player.builder()
                            .name(playerDto.getName())
                            .lovedGames(new HashSet<>())
                            .build();
                    return playerRepository.save(newPlayer);
                });
    }

    @Override
    public Player getPlayer(long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("No player with id " + id));
    }

    @Override
    public Player getPlayerByName(String name) {
        return playerRepository.findByName(name)
                .orElseThrow(() -> new PlayerNotFoundException("No player with name " + name));
    }

    @Override
    public Set<Game> getGamesByPlayerId(long id) {
        return getPlayer(id).getLovedGames();
    }
}
