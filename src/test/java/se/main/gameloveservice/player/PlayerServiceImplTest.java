package se.main.gameloveservice.player;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.main.gameloveservice.exception.PlayerNotFoundException;
import se.main.gameloveservice.game.Game;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Mock
    private PlayerRepository playerRepository;

    @Test
    void givenId_whenGetPlayerById_thenPlayer() {
        var id = 1L;
        var player = Player.builder().build();
        when(playerRepository.findById(id)).thenReturn(Optional.of(player));

        var result = playerService.getPlayer(id);
        assertEquals(player, result);
    }

    @Test
    void givenIdIsMissing_whenGetPlayerById_thenPlayer() {
        var id = 1L;
        when(playerRepository.findById(id)).thenReturn(Optional.empty());

        var ex = assertThrows(PlayerNotFoundException.class, () -> playerService.getPlayer(id));
        assertEquals("No player with id " + id, ex.getMessage());
    }

    @Test
    void givenPlayerDtoWithNewPlayer_whenGetPlayer_thenPlayerIsSaved() {
        var playerDto = PlayerDto.builder()
                .build();
        var player = Player.builder().build();
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        var result = playerService.createPlayer(playerDto);
        assertEquals(player, result);
    }

    @Test
    void givenPlayerDtoWithExistingPlayer_whenGetPlayer_thenExistingPlayer() {
        var playerDto = PlayerDto.builder()
                .name("Player1")
                .build();
        var player = Player.builder().build();
        when(playerRepository.findByName(anyString())).thenReturn(Optional.of(player));

        var result = playerService.createPlayer(playerDto);
        assertEquals(player, result);
        verify(playerRepository, never()).save(any());
    }

    @Test
    void givenId_whenGetGamesByPlayer_thenGames() {
        var id = 1L;
        var gameName = "Game1";
        var game = Game.builder().name(gameName).build();
        var player = Player.builder()
                .lovedGames(Set.of(game))
                .build();
        when(playerRepository.findById(id)).thenReturn(Optional.of(player));

        var result = playerService.getGamesByPlayerId(id);
        assertEquals(1, result.size());
        assertEquals(gameName, result.iterator().next().getName());
    }

    @Test
    void givenName_whenGetPlayerById_thenPlayer() {
        var name = "name";
        var player = Player.builder().build();
        when(playerRepository.findByName(name)).thenReturn(Optional.of(player));

        var result = playerService.getPlayerByName(name);
        assertEquals(player, result);
    }

    @Test
    void givenNameIsMissing_whenGetPlayerById_thenException() {
        var name = "name";
        when(playerRepository.findByName(name)).thenReturn(Optional.empty());

        var ex = assertThrows(PlayerNotFoundException.class, () ->
                playerService.getPlayerByName(name));
        assertEquals("No player with name " + name, ex.getMessage());
    }

}
