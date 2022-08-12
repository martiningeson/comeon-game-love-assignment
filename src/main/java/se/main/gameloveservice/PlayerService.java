package se.main.gameloveservice;

import se.main.gameloveservice.game.Game;
import se.main.gameloveservice.player.Player;
import se.main.gameloveservice.player.PlayerDto;

import java.util.Set;

public interface PlayerService {

    Player createPlayer(PlayerDto playerDto);

    Player getPlayer(long id);

    Player getPlayerByName(String name);

    Set<Game> getGamesByPlayerId(long id);

}
