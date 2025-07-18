package com.lss.l1sbunitintegrationtestscontrolleradvice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lss.l1sbunitintegrationtestscontrolleradvice.L1SbUnitIntegrationTestsControllerAdviceApplication;
import com.lss.l1sbunitintegrationtestscontrolleradvice.entity.Mission;
import com.lss.l1sbunitintegrationtestscontrolleradvice.entity.MissionStatus;
import com.lss.l1sbunitintegrationtestscontrolleradvice.entity.Starship;
import com.lss.l1sbunitintegrationtestscontrolleradvice.repository.MissionRepository;
import com.lss.l1sbunitintegrationtestscontrolleradvice.repository.StarshipRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {L1SbUnitIntegrationTestsControllerAdviceApplication.class,},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class BaseControllerIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MissionRepository missionRepository;
    @Autowired
    protected StarshipRepository starshipRepository;

    @BeforeEach
    protected void setUp() {
        missionRepository.deleteAll();
        starshipRepository.deleteAll();
    }

    protected Starship creaStarship(String captainName) {
        return Starship.builder()
                .name(captainName)
                .captain(String.format("Captain %s", captainName))
                .maxCrewSize(100)
                .build();
    }

    protected Mission createMission(Starship starship, String s) {
        return Mission.builder()
                .codename(s)
                .objective(String.format("Objective for %s", s))
                .status(MissionStatus.ACTIVE)
                .starship(starship)
                .build();
    }


}
