package com.lss.l1sbunitintegrationtestscontrolleradvice.controller;

import com.lss.l1sbunitintegrationtestscontrolleradvice.dto.MissionDto;
import com.lss.l1sbunitintegrationtestscontrolleradvice.sercvice.FleetService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/starships")
@RequiredArgsConstructor
public class FleetController {

    private final FleetService fleetService;

    @GetMapping("/{starshipId}/missions")
    public ResponseEntity<List<MissionDto>> getMissionsByStarship(@PathVariable Long starshipId) {
        return ResponseEntity.ok(fleetService.getMissionsByStarship(starshipId));
    }

    @GetMapping("/missions/{missionId}")
    public ResponseEntity<MissionDto> getMissionById(@PathVariable Long missionId) {
        return ResponseEntity.ok(fleetService.getMissionById(missionId));
    }
}