package se.main.gameloveservice.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.main.gameloveservice.GameService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @InjectMocks
    private GameController gameController;

    @Mock
    private GameService gameService;

    @Test
    void testGetTopLovedGames() {
        Stream<Game> topGames = Stream.of();
        when(gameService.getTopLovedGames(10)).thenReturn(topGames);

        var result = gameController.getTopLovedGames(10);
        assertEquals(topGames, result);
    }
}
