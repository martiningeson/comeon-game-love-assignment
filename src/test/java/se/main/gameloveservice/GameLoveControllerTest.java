package se.main.gameloveservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.main.gameloveservice.player.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameLoveControllerTest {

    @InjectMocks
    private GameLoveController gameLoveController;

    @Mock
    private GameLoveServiceFacade gameLoveServiceFacade;

    @Test
    void givenLoveGameDto_whenAddEntry_thenPlayer() {
        var loveGameDto = new LoveGameDto();
        var player = Player.builder().build();
        when(gameLoveServiceFacade.addEntry(loveGameDto)).thenReturn(player);

        var result = gameLoveController.addEntry(loveGameDto);
        assertEquals(player, result);
    }

    @Test
    void givenUnloveGameDto_whenAddEntry_thenPlayer() {
        var unloveGameDto = new UnloveGameDto(null, null);

        gameLoveController.deleteEntry(unloveGameDto);
        verify(gameLoveServiceFacade).deleteEntry(unloveGameDto);
    }

}
