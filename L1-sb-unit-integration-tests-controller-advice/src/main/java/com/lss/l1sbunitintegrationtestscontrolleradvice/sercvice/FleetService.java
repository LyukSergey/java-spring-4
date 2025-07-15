package com.lss.l1sbunitintegrationtestscontrolleradvice.sercvice;

import com.lss.l1sbunitintegrationtestscontrolleradvice.dto.MissionDto;
import java.util.List;

public interface FleetService {

    List<MissionDto> getMissionsByStarship(Long starshipId);

    MissionDto getMissionById(Long missionId);
}
