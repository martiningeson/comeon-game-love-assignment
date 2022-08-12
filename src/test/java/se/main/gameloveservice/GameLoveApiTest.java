package se.main.gameloveservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import se.main.gameloveservice.game.GameDto;
import se.main.gameloveservice.player.PlayerDto;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameLoveApiTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String GAMELOVE_URL = "/gamelove";

    @Autowired
    private GameLoveServiceFacade gameLoveServiceFacade;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPostEntry() throws Exception {
        String playerName = getRandomName();
        String gameName = getRandomName();
        var lovaGameDto = LoveGameDto.builder()
                .player(PlayerDto.builder()
                        .name(playerName)
                        .build())
                .game(new GameDto(gameName))
                .build();

        var servletResponse = mockMvc.perform(post(GAMELOVE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(lovaGameDto)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.name").value(playerName))
                .andExpect(jsonPath("$.lovedGames.*", hasSize(1)))
                .andExpect(jsonPath("$.lovedGames[0].name").value(gameName))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        int id = JsonPath.parse(servletResponse.getContentAsString()).read("$.id");
        assertNotNull(playerService.getPlayer(id));
    }

    @Test
    void testDeleteEntry() throws Exception {
        // Add player
        String playerName = getRandomName();
        String gameName = getRandomName();
        var lovaGameDto = LoveGameDto.builder()
                .player(PlayerDto.builder()
                        .name(playerName)
                        .build())
                .game(new GameDto(gameName))
                .build();

        mockMvc.perform(post(GAMELOVE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(lovaGameDto)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.name").value(playerName))
                .andExpect(jsonPath("$.lovedGames.*", hasSize(1)))
                .andExpect(jsonPath("$.lovedGames[0].name").value(gameName))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        // Unlove game
        var unloveGameDto = UnloveGameDto.builder()
                .player(PlayerDto.builder()
                        .name(playerName)
                        .build())
                .game(new GameDto(gameName))
                .build();

        mockMvc.perform(delete(GAMELOVE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(unloveGameDto)))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse();

        assertTrue(playerService.getPlayerByName(playerName).getLovedGames().isEmpty());
    }

    private String getRandomName() {
        return UUID.randomUUID().toString();
    }

}
