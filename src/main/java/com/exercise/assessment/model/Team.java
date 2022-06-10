package com.exercise.assessment.model;

import com.exercise.assessment.dto.TeamDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @NonNull
    private String id;

    @NonNull
    private String name;

    public TeamDTO convertEntityToDTO() {
        return new ModelMapper().map(this, TeamDTO.class);
    }
}
