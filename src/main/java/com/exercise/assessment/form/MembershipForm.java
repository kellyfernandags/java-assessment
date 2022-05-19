package com.exercise.assessment.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MembershipForm {

	@NotNull @NotEmpty
	private String teamId;
	@NotNull @NotEmpty
	private String userId;
	
	public MembershipForm(String teamId, String userId) {
		this.teamId = teamId;
		this.userId = userId;
	}

	public String getTeamId() {
		return teamId;
	}

	public String getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return String.format("Membership Data [teamId=%s, userId=%s]", teamId, userId);
	}
	
}
