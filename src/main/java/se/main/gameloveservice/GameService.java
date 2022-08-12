package se.main.gameloveservice;

import se.main.gameloveservice.game.Game;
import se.main.gameloveservice.game.GameDto;

import java.util.stream.Stream;

public interface GameService {

    Game getGame(GameDto gameDto);

    Game getGameByName(String name);

    Stream<Game> getTopLovedGames(int nr);

}
