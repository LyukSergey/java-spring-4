package com.lss.l1sbunitintegrationtestscontrolleradvice.repository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.lss.l1sbunitintegrationtestscontrolleradvice.entity.Mission;
import com.lss.l1sbunitintegrationtestscontrolleradvice.entity.Starship;
import java.util.List;
import org.junit.jupiter.api.Test;

class MissionRepositoryIT extends AbstractJpaTest {

    @Test
    void findByStarshipId() {
        // Given
        final Starship starship = starshipRepository.save(creaStarship("Starship 1"));
        final Mission mission1 = createMission(starship, "Mission 1");
        final Mission mission2 = createMission(starship, "Mission 2");
        missionRepository.saveAll(List.of(mission1, mission2));

        // When
        List<Mission> missions = missionRepository.findByStarshipId(starship.getId());

        // Then
        assertThat(missions)
                .isNotEmpty()
                .hasSize(2);
        assertThat(missions).allSatisfy(mission -> {
            assertThat(mission).hasNoNullFieldsOrPropertiesExcept("id");
            assertThat(mission.getStarship()).isNotNull();
        });
    }

}