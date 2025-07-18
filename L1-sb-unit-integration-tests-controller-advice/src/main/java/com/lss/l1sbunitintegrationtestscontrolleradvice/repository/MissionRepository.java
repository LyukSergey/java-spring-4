package com.lss.l1sbunitintegrationtestscontrolleradvice.repository;

import com.lss.l1sbunitintegrationtestscontrolleradvice.entity.Mission;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findByStarshipId(Long starshipId);

}
