package com.exercise.assessment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NonNull
    private String id;

    @NonNull
    private String displayName;

    @NonNull
    private RoleDTO role;
}
