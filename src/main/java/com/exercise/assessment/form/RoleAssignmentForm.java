package com.exercise.assessment.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class RoleAssignmentForm {
	@NotNull @NonNull
	private String userId;

	private Long roleId;
}
