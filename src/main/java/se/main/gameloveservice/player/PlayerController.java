package se.main.gameloveservice.player;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import se.main.gameloveservice.PlayerService;
import se.main.gameloveservice.game.Game;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Player getPlayerById(@PathVariable("id") long id) {
        return playerService.getPlayer(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Player getPlayerByName(@RequestParam("name") String name) {
        return playerService.getPlayerByName(name);
    }

    @GetMapping(value = "{id}/games", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Game> getGamesByPlayerId(@PathVariable("id") long id) {
        return playerService.getGamesByPlayerId(id);
    }

}
