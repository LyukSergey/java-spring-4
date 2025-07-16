package com.example.fleet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FleetServiceImplTest {

    @Mock
    private MissionRepository fleetRepository;

    @InjectMocks
    private FleetServiceImpl fleetService;

    @Test
    void getMissionByIdShouldReturnMissionById() {
        // Given
        final Long missionId = 1L;
        final Mission mission = /* створити тестовий об'єкт */;
        when(fleetRepository.findById(missionId))
                .thenReturn(java.util.Optional.of(mission));

        // When
        MissionDto missionDto = fleetService.getMissionById(missionId);

        // Then
        assertThat(missionDto).isNotNull();
        verify(fleetRepository).findById(missionId);
    }
}
