package com.lss.l1sbunitintegrationtestscontrolleradvice.sercvice.impl;

import com.lss.l1sbunitintegrationtestscontrolleradvice.dto.MissionDto;
import com.lss.l1sbunitintegrationtestscontrolleradvice.exception.ResourceNotFoundException;
import com.lss.l1sbunitintegrationtestscontrolleradvice.repository.MissionRepository;
import com.lss.l1sbunitintegrationtestscontrolleradvice.repository.StarshipRepository;
import com.lss.l1sbunitintegrationtestscontrolleradvice.sercvice.FleetService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FleetServiceImpl implements FleetService {

    private final MissionRepository missionRepository;

    @Override
    @Transactional
    public List<MissionDto> getMissionsByStarship(Long starshipId) {
        return missionRepository.findByStarshipId(starshipId).stream()
                .map(mission -> new MissionDto(mission.getId(), mission.getCodename(), mission.getObjective(), mission.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MissionDto getMissionById(Long missionId) {
        return missionRepository.findById(missionId)
                .map(mission -> new MissionDto(mission.getId(), mission.getCodename(), mission.getObjective(), mission.getStatus()))
                .orElseThrow(() -> new ResourceNotFoundException("Mission with id " + missionId + " was not found"));
    }
}
