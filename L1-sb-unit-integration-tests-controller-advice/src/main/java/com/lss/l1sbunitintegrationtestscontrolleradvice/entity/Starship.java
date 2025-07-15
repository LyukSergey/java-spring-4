package com.lss.l1sbunitintegrationtestscontrolleradvice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "starships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//переклад - зореліт
public class Starship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String captain;
    @Column(name = "max_crew_size")
    private Integer maxCrewSize;

    @OneToMany(mappedBy = "starship")
    private List<Mission> missions;
}
