package com.exercise.assessment.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.exercise.assessment.model.Role;

public class RoleForm {

	@NotNull @NotEmpty
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
	public Role convert() {
		return new Role(name);
	}
}
