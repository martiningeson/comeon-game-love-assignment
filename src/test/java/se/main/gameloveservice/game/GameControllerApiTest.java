package se.main.gameloveservice.game;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    //Database is initialized with 10 games during startup.
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "10"})
    void testGetTopLovedGames(String nr) throws Exception {
        mockMvc.perform(get("/games/toplovedgames")
                        .param("nr", nr))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(Integer.parseInt(nr))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    }
}
