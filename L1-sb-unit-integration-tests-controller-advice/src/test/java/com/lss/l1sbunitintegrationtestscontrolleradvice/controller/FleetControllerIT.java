package com.lss.l1sbunitintegrationtestscontrolleradvice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lss.l1sbunitintegrationtestscontrolleradvice.entity.Mission;
import com.lss.l1sbunitintegrationtestscontrolleradvice.entity.Starship;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

class FleetControllerIT extends BaseControllerIntegrationTest {

    @Test
    @SneakyThrows
    void getMissionsByStarship() {
        // Given
        final Starship starship = starshipRepository.save(creaStarship("Starship 1"));
        final Mission mission = missionRepository.save(createMission(starship, "Mission 1"));

        // When, Then
        mockMvc.perform(get("/starships/{starshipId}/missions", starship.getId()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$[0].codename").value(mission.getCodename()))
                .andExpect(jsonPath("$[0].objective").value(mission.getObjective()))
                .andExpect(jsonPath("$[0].status").value(mission.getStatus().name()));
    }

    @Test
    @SneakyThrows
    void getMissionByIdShouldReturnResourceNotFoundIfMissionIsNotFound() {
        // Given
        final long notExistingMissionId = 1000L;
        final String expectedMessage = String.format("Mission with id %s was not found", notExistingMissionId);

        // When, Then
        mockMvc.perform(get("/starships/missions/{missionId}", notExistingMissionId))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

}