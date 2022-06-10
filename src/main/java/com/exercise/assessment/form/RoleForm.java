package com.exercise.assessment.form;

import javax.validation.constraints.NotNull;

import com.exercise.assessment.model.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
public class RoleForm {

	@NonNull @NotNull
	private String name;

	public Role convert() {
		return new Role(name);
	}
}
