package se.main.gameloveservice.game;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import se.main.gameloveservice.GameService;
import se.main.gameloveservice.exception.PlayerNotFoundException;

import java.util.stream.Stream;

@Service
class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game getGame(GameDto gameDto) {
        return gameRepository.findByName(gameDto.getName())
                .orElseGet(() -> {
                    var newPlayer = Game.builder()
                            .name(gameDto.getName())
                            .build();
                    return gameRepository.save(newPlayer);
                });
    }

    @Override
    public Game getGameByName(String name) {
        return gameRepository.findByName(name)
                .orElseThrow(() -> new PlayerNotFoundException("No game with name " + name));
    }

    @Override
    public Stream<Game> getTopLovedGames(int nr) {
        var pageable = PageRequest.of(0, nr, Sort.Direction.DESC, "loves");
        return gameRepository.findAll(pageable)
                .stream();
    }
}
