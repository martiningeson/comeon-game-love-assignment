package se.main.gameloveservice;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.main.gameloveservice.player.Player;

@Service
public class GameLoveServiceFacade {
    private final GameService gameService;
    private final PlayerService playerService;

    public GameLoveServiceFacade(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @Transactional
    public Player addEntry(LoveGameDto loveGameDto) {
        // Get or create player
        var player = playerService.createPlayer(loveGameDto.getPlayer());

        // Get or create game
        var game = gameService.getGame(loveGameDto.getGame());

        if (player.getLovedGames().add(game)) {
            game.gotLoved();
        }

        return player;
    }

    @Transactional
    public void deleteEntry(UnloveGameDto unloveGameDto) {
        var player = playerService.getPlayerByName(unloveGameDto.getPlayer().getName());
        var game = gameService.getGameByName(unloveGameDto.getGame().getName());
        if (player.getLovedGames().remove(game)) {
            game.gotUnloved();
        }
    }

}
