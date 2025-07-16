package com.example.fleet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FleetControllerIT extends BaseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StarshipRepository starshipRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Test
    void getMissionsByStarship() throws Exception {
        final Starship starship = starshipRepository.save(/* створити Starship */);
        final Mission mission = missionRepository.save(/* створити Mission */);

        mockMvc.perform(get("/starships/{starshipId}/missions", starship.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codename").value(mission.getCodename()));
    }
}
