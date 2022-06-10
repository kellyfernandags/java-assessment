package com.exercise.assessment.model;

import com.exercise.assessment.dto.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @NonNull
    private String id;

    @NonNull
    private String displayName;

    @ManyToOne
    @NonNull
    private Role role;

    public UserDTO convertEntityToDTO() {
        return new ModelMapper().map(this, UserDTO.class);
    }
}
