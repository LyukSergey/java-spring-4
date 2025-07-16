package com.lss.l1sbunitintegrationtestscontrolleradvice.repository;

import com.lss.l1sbunitintegrationtestscontrolleradvice.entity.Starship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarshipRepository extends JpaRepository<Starship, Long> {

}
