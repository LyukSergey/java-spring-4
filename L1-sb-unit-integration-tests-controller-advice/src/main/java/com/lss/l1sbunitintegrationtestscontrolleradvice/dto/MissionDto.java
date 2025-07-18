package com.lss.l1sbunitintegrationtestscontrolleradvice.dto;

import com.lss.l1sbunitintegrationtestscontrolleradvice.entity.MissionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MissionDto {

    private Long id;
    private String codename;
    private String objective;
    private MissionStatus status;

}
