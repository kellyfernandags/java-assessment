package com.exercise.assessment.model;

import com.exercise.assessment.dto.MembershipDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NonNull
    private User user;

    @ManyToOne
    @NonNull
    private Team team;

    public MembershipDTO convertEntityToDTO() {
        return new ModelMapper().map(this, MembershipDTO.class);
    }
}
