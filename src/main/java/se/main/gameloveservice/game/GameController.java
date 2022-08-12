package se.main.gameloveservice.game;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.main.gameloveservice.GameService;

import java.util.stream.Stream;

@RestController
@RequestMapping("/games/")
@Validated
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value = "toplovedgames", produces = MediaType.APPLICATION_JSON_VALUE)
    public Stream<Game> getTopLovedGames(@RequestParam("nr") int nr) {
        return gameService.getTopLovedGames(nr);
    }
}
