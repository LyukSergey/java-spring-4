package com.lss.l1sbunitintegrationtestscontrolleradvice.sercvice.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lss.l1sbunitintegrationtestscontrolleradvice.dto.MissionDto;
import com.lss.l1sbunitintegrationtestscontrolleradvice.entity.Mission;
import com.lss.l1sbunitintegrationtestscontrolleradvice.entity.MissionStatus;
import com.lss.l1sbunitintegrationtestscontrolleradvice.exception.ResourceNotFoundException;
import com.lss.l1sbunitintegrationtestscontrolleradvice.repository.MissionRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FleetServiceImplTest {

    @InjectMocks
    private FleetServiceImpl fleetService;
    @Mock
    private MissionRepository fleetRepository;

    @Test
    void getMissionsByStarshipShouldReturnMissionByStarshipId() {
        // Given
        final Long starshipId = 1L;
        final Mission mission1 = Mockito.mock(Mission.class);
        final Mission mission2 = Mockito.mock(Mission.class);

        when(fleetRepository.findByStarshipId(starshipId)).thenReturn(List.of(mission1, mission2));

        // When
        List<MissionDto> missions = fleetService.getMissionsByStarship(starshipId);

        // Then
        assertThat(missions)
                .isNotEmpty()
                .hasSize(2);
        verify(fleetRepository).findByStarshipId(starshipId);
    }

    @Test
    void getMissionByIdShouldReturnMissionById() {
        // Given
        final Long missionId = 1L;
        final Mission mission = Mockito.mock(Mission.class);
        when(mission.getId()).thenReturn(missionId);
        when(mission.getCodename()).thenReturn("Test Codename");
        when(mission.getObjective()).thenReturn("Test Objective");
        when(mission.getStatus()).thenReturn(MissionStatus.ACTIVE);

        when(fleetRepository.findById(missionId))
                .thenReturn(java.util.Optional.of(mission));

        // When
        MissionDto missionDto = fleetService.getMissionById(missionId);

        // Then
        assertThat(missionDto)
                .isNotNull()
                .extracting(MissionDto::getId, MissionDto::getCodename, MissionDto::getObjective, MissionDto::getStatus)
                .containsExactly(missionId, "Test Codename", "Test Objective", MissionStatus.ACTIVE);

        verify(fleetRepository).findById(missionId);
    }

    @Test
    void getMissionByIdShouldThrowResourceNotFoundExceptionWhenMissionNotFound() {
        // Given
        final Long missionId = 1L;
        final String expectedMessage = String.format("Mission with id %s was not found", missionId);

        when(fleetRepository.findById(missionId)).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThatThrownBy(() -> fleetService.getMissionById(missionId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedMessage);
    }

}