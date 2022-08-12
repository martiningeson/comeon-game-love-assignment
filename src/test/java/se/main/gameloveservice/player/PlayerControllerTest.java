package se.main.gameloveservice.player;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.main.gameloveservice.PlayerService;
import se.main.gameloveservice.game.Game;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @InjectMocks
    private PlayerController playerController;

    @Mock
    private PlayerService playerService;

    @Test
    void givenId_whenGetPlayerById_thenPlayer() {
        var id = 1L;
        var player = Player.builder().build();
        when(playerService.getPlayer(id)).thenReturn(player);

        var result = playerController.getPlayerById(id);
        assertEquals(player, result);
    }

    @Test
    void givenId_whenGetGamesByPlayerId_thenGames() {
        var id = 1L;
        Set<Game> games = Collections.emptySet();
        when(playerService.getGamesByPlayerId(id)).thenReturn(games);

        var result = playerController.getGamesByPlayerId(id);
        assertEquals(games, result);
    }

    @Test
    void givenName_whenPlayerByName_thenPlayer() {
        var name = "name";
        var player = Player.builder().build();
        when(playerService.getPlayerByName(name)).thenReturn(player);

        var result = playerController.getPlayerByName(name);
        assertEquals(player, result);
    }
}
