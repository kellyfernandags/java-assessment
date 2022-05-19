package com.exercise.assessment.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RoleAssignmentForm {

	@NotNull @NotEmpty
	private String userId;
	@NotNull
	private Long roleId;
	
	
	public String getUserId() {
		return userId;
	}
	public Long getRoleId() {
		return roleId;
	}
}
