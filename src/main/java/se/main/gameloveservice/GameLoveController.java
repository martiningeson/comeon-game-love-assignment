package se.main.gameloveservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import se.main.gameloveservice.player.Player;

import javax.validation.Valid;

@RestController
@RequestMapping("/gamelove")
public class GameLoveController {

    private final GameLoveServiceFacade gameLoveServiceFacade;

    public GameLoveController(GameLoveServiceFacade gameLoveServiceFacade) {
        this.gameLoveServiceFacade = gameLoveServiceFacade;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Player addEntry(@Valid @RequestBody LoveGameDto loveGameDto) {
        return gameLoveServiceFacade.addEntry(loveGameDto);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEntry(@Valid @RequestBody UnloveGameDto unloveGameDto) {
        gameLoveServiceFacade.deleteEntry(unloveGameDto);
    }
}
