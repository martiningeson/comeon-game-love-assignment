package se.main.gameloveservice;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.main.gameloveservice.game.Game;
import se.main.gameloveservice.game.GameDto;
import se.main.gameloveservice.player.Player;
import se.main.gameloveservice.player.PlayerDto;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameLoveServiceFacadeTest {

    @InjectMocks
    private GameLoveServiceFacade gameLoveServiceFacade;

    @Mock
    private GameService gameService;

    @Mock
    private PlayerService playerService;

    @Test
    void givenLoveGameDto_whenAddEntry_thenGameGotLoved() {
        // Mock Player
        String playerName = "Player";
        var player = Player.builder()
                .name(playerName)
                .lovedGames(new HashSet<>())
                .build();
        var playerDto = new PlayerDto(playerName);
        when(playerService.createPlayer(playerDto)).thenReturn(player);

        // Mock game
        var game = mock(Game.class);
        var gameDto = new GameDto("Game");
        when(gameService.getGame(gameDto)).thenReturn(game);

        // AddEntry
        var loveGameDto = LoveGameDto.builder()
                .game(gameDto)
                .player(playerDto)
                .build();
        gameLoveServiceFacade.addEntry(loveGameDto);

        assertTrue(player.getLovedGames().contains(game));
        verify(game).gotLoved();
    }

    @Test
    void givenLoveGameDtoForAlreadyLovedGame_whenAddEntry_thenGameDidNotGetLoved() {
        // Mock game
        var game = mock(Game.class);

        // Mock Player
        String playerName = "Player";
        var player = Player.builder()
                .name(playerName)
                .lovedGames(new HashSet<>(Set.of(game)))
                .build();
        var playerDto = new PlayerDto(playerName);
        when(playerService.createPlayer(playerDto)).thenReturn(player);

        var gameDto = new GameDto("Game");
        when(gameService.getGame(gameDto)).thenReturn(game);

        // AddEntry
        var loveGameDto = LoveGameDto.builder()
                .game(gameDto)
                .player(playerDto)
                .build();
        gameLoveServiceFacade.addEntry(loveGameDto);

        assertTrue(player.getLovedGames().contains(game));
        verify(game, never()).gotLoved();
    }

    @Test
    void givenUnloveGameDto_whenDeleteEntry_thenGameGotUnloved() {
        var game = mock(Game.class);
        var player = Player.builder()
                .lovedGames(new HashSet<>(Set.of(game)))
                .build();

        String playerName = "Player";
        when(playerService.getPlayerByName(playerName)).thenReturn(player);
        String gameName = "Game";
        when(gameService.getGameByName(gameName)).thenReturn(game);

        // deleteEntry
        var playerDto = new PlayerDto(playerName);
        var gameDto = new GameDto(gameName);
        UnloveGameDto unloveGameDto = UnloveGameDto.UnloveGameDtoBuilder()
                .player(playerDto)
                .game(gameDto)
                .build();
        gameLoveServiceFacade.deleteEntry(unloveGameDto);

        assertFalse(player.getLovedGames().contains(game));
        verify(game).gotUnloved();
    }

    @Test
    void givenUnloveGameDtoForNotLovedGame_whenDeleteEntry_thenGameDidNotGetUnloved() {
        var game = mock(Game.class);
        var player = Player.builder()
                .lovedGames(new HashSet<>())
                .build();

        String playerName = "Player";
        when(playerService.getPlayerByName(playerName)).thenReturn(player);
        String gameName = "Game";
        when(gameService.getGameByName(gameName)).thenReturn(game);

        // deleteEntry
        var playerDto = new PlayerDto(playerName);
        var gameDto = new GameDto(gameName);
        UnloveGameDto unloveGameDto = UnloveGameDto.UnloveGameDtoBuilder()
                .player(playerDto)
                .game(gameDto)
                .build();
        gameLoveServiceFacade.deleteEntry(unloveGameDto);

        verify(game, never()).gotUnloved();
    }


}
