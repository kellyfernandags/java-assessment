package com.exercise.assessment.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Membership {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private User user;
	@ManyToOne
	private Team team;

	public Membership() {
	}

	public Membership(User user, Team team) {
		this.user = user;
		this.team = team;
	}
	
	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Team getTeam() {
		return team;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return String.format("Membership [user=%s, team=%s]", user, team);
	}

}
