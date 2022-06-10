package com.exercise.assessment.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
public class MembershipForm {

	@NotNull @NotEmpty @NonNull
	private String teamId;
	
	@NotNull @NotEmpty @NonNull
	private String userId;
}
