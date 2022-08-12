package se.main.gameloveservice.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @InjectMocks
    private GameServiceImpl gameService;

    @Mock
    private GameRepository gameRepository;

    @Captor
    private ArgumentCaptor<Pageable> pageableArgumentCaptor;

    @Test
    void givenGameDtoWithNewGame_whenGetGame_thenGameIsSaved() {
        var gameDto = new GameDto("Game");
        var game = new Game();
        when(gameRepository.save(any())).thenReturn(game);

        var result = gameService.getGame(gameDto);
        assertEquals(game, result);
    }

    @Test
    void givenGameDtoWithNewGame_whenGetGame_thenGameExistingGame() {
        String gameName = "Game";
        var gameDto = new GameDto(gameName);
        var game = Game.builder()
                .name(gameName)
                .build();
        when(gameRepository.findByName(gameName)).thenReturn(Optional.of(game));

        var result = gameService.getGame(gameDto);
        assertEquals(game, result);
        verify(gameRepository, never()).save(any());
    }

    @Test
    void givenExistingName_whenGetByName_thenGame() {
        String gameName = "Game";
        var game = new Game();
        when(gameRepository.findByName(gameName)).thenReturn(Optional.of(game));

        var result = gameService.getGameByName(gameName);
        assertEquals(game, result);
    }

    @Test
    void givenMissingName_whenGetByName_thenException() {
        String gameName = "Game";
        when(gameRepository.findByName(gameName)).thenReturn(Optional.empty());

        var ex = assertThrows(RuntimeException.class, () -> gameService.getGameByName(gameName));
        assertEquals("No game with name " + gameName, ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10})
    void givenNr_whenGetTopLovedGames_thenStream(int nr) {
        when(gameRepository.findAll(any(PageRequest.class))).thenReturn(Page.empty());
        gameService.getTopLovedGames(nr);

        verify(gameRepository).findAll(pageableArgumentCaptor.capture());
        var pageable = pageableArgumentCaptor.getValue();
        assertEquals(0, pageable.getPageNumber());
        assertEquals(nr, pageable.getPageSize());
    }
}
