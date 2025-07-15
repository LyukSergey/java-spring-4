package com.lss.l1sbunitintegrationtestscontrolleradvice.repository;

import com.lss.l1sbunitintegrationtestscontrolleradvice.SchemaInit;
import com.lss.l1sbunitintegrationtestscontrolleradvice.entity.Mission;
import com.lss.l1sbunitintegrationtestscontrolleradvice.entity.MissionStatus;
import com.lss.l1sbunitintegrationtestscontrolleradvice.entity.Starship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableJpaRepositories(basePackages = "com.lss.l1sbunitintegrationtestscontrolleradvice.repository")
@ContextConfiguration(classes = {SchemaInit.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AbstractJpaTest {

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
