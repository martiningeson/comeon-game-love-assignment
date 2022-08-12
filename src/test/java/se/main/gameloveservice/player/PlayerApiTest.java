package se.main.gameloveservice.player;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import se.main.gameloveservice.game.Game;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PlayerApiTest {

    private static final String PLAYER_URL = "/players/";
    private static final String PLAYER_NAME = "Player1";
    private static final String GAME_NAME = "Game1";

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetPlayer() throws Exception {
        var id = addPlayer();
        mockMvc.perform(get(PLAYER_URL + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.name").value(PLAYER_NAME))
                .andExpect(jsonPath("$.lovedGames.*", hasSize(1)))
                .andExpect(jsonPath("$.lovedGames[0].name").value(GAME_NAME))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    }

    @Test
    void testGetNonExistingPlayer() throws Exception {
        var playerId = -1;
        // Get newly added player
        mockMvc.perform(get(PLAYER_URL + playerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetGamesForPlayerId() throws Exception {
        var id = addPlayer();
        mockMvc.perform(get(PLAYER_URL + id + "/games")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.[0].name").value(GAME_NAME))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    }

    @Test
    void testGetPlayerByName() throws Exception {
        addPlayer();
        mockMvc.perform(get(PLAYER_URL)
                        .param("name", PLAYER_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.name").value(PLAYER_NAME))
                .andExpect(jsonPath("$.lovedGames.*", hasSize(1)))
                .andExpect(jsonPath("$.lovedGames[0].name").value(GAME_NAME))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    }

    private long addPlayer() {
        var game = Game.builder()
                .name(GAME_NAME)
                .players(new HashSet<>())
                .build();

        var player = Player.builder()
                .name(PLAYER_NAME)
                .lovedGames(Set.of(game))
                .build();
        return playerRepository.save(player).getId();
    }

}
